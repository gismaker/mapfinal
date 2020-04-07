/**
 * Copyright (c) 2015-2017, Henry Yang 杨勇 (gismail@foxmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mapfinal.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EventManager {

	private final ExecutorService threadPool;
	private final Map<String, List<EventListener>> asyncListenerMap;
	private final Map<String, List<EventListener>> listenerMap;

	private static EventManager manager;

	private EventManager() {
		//threadPool = Executors.newFixedThreadPool(5);
		threadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.MINUTES,
                new SynchronousQueue<Runnable>());
		asyncListenerMap = new ConcurrentHashMap<String, List<EventListener>>();
		listenerMap = new ConcurrentHashMap<String, List<EventListener>>();
	}

	public static EventManager me() {
		if(manager==null) {
			manager = new EventManager();
		}
		return manager;
	}

	public void unRegisterListener(Class<? extends EventListener> listenerClass) {
		deleteListner(listenerMap, listenerClass);
		deleteListner(asyncListenerMap, listenerClass);
	}

	private void deleteListner(Map<String, List<EventListener>> map, Class<? extends EventListener> listenerClass) {
		for (Map.Entry<String, List<EventListener>> entry : map.entrySet()) {
			EventListener deleteListener = null;
			for (EventListener listener : entry.getValue()) {
				if (listener.getClass() == listenerClass) {
					deleteListener = listener;
				}
			}
			if (deleteListener != null) {
				entry.getValue().remove(deleteListener);
			}
		}
	}
	
	public void unRegisterListener(String eventAction, Class<? extends EventListener> listenerClass) {
		deleteListner(listenerMap.get(eventAction), listenerClass);
		deleteListner(asyncListenerMap.get(eventAction), listenerClass);
	}

	private void deleteListner(List<EventListener> listenerList, Class<? extends EventListener> listenerClass) {
		if(listenerList==null) return;
		EventListener deleteListener = null;
		for (EventListener listener : listenerList) {
			if (listener.getClass() == listenerClass) {
				deleteListener = listener;
			}
		}
		if (deleteListener != null) {
			listenerList.remove(deleteListener);
		}
	}


	public void registerListener(Class<? extends EventListener> listenerClass) {
		if (listenerClass == null) {
			return;
		}

		Listener listenerAnnotation = listenerClass.getAnnotation(Listener.class);
		if (listenerAnnotation == null) {
			System.out.println("listenerClass[" + listenerAnnotation + "] resigter fail,because not use Listener annotation.");
			return;
		}

		String[] actions = listenerAnnotation.action();
		if (actions == null || actions.length == 0) {
			System.out.println("listenerClass[" + listenerAnnotation + "] resigter fail,because action is null or blank.");
			return;
		}

		if (listenerHasRegisterBefore(listenerClass)) {
			return;
		}

		EventListener listener = newListener(listenerClass);
		if (listener == null) {
			return;
		}

		for (String action : actions) {
			List<EventListener> list = null;
			if (listenerAnnotation.async()) {
				list = asyncListenerMap.get(action);
			} else {
				list = listenerMap.get(action);
			}
			if (null == list) {
				list = new ArrayList<EventListener>();
			}
			if (list.isEmpty() || !list.contains(listener)) {
				list.add(listener);
			}
			Collections.sort(list, new Comparator<EventListener>() {
				@Override
				public int compare(EventListener o1, EventListener o2) {
					Listener l1 = o1.getClass().getAnnotation(Listener.class);
					Listener l2 = o2.getClass().getAnnotation(Listener.class);
					return l1.weight() - l2.weight();
				}
			});

			if (listenerAnnotation.async()) {
				asyncListenerMap.put(action, list);
			} else {
				listenerMap.put(action, list);
			}
		}
		
		//if (getConstants().getDevMode()) {
			//System.out.println(String.format("listener[%s]-->>registered.", listener));
		//}

	}
	
	public void registerListener(String eventAction, Class<? extends EventListener> listenerClass) {
		if (listenerClass == null) {
			return;
		}

		String[] actions = null;
		boolean async = true;
		Listener listenerAnnotation = listenerClass.getAnnotation(Listener.class);
		if (listenerAnnotation == null) {
			actions = new String[1];
			actions[0] = eventAction;
		} else {
			actions = listenerAnnotation.action();
			async = listenerAnnotation.async();
		}
		
		if (actions == null || actions.length == 0) {
			System.out.println("listenerClass[" + listenerAnnotation + "] resigter fail,because action is null or blank.");
			return;
		}

		if (listenerHasRegisterBefore(listenerClass)) {
			return;
		}

		EventListener listener = newListener(listenerClass);
		if (listener == null) {
			return;
		}

		for (String action : actions) {
			List<EventListener> list = null;
			if (async) {
				list = asyncListenerMap.get(action);
			} else {
				list = listenerMap.get(action);
			}
			if (null == list) {
				list = new ArrayList<EventListener>();
			}
			if (list.isEmpty() || !list.contains(listener)) {
				list.add(listener);
			}
			Collections.sort(list, new Comparator<EventListener>() {
				@Override
				public int compare(EventListener o1, EventListener o2) {
					Listener l1 = o1.getClass().getAnnotation(Listener.class);
					Listener l2 = o2.getClass().getAnnotation(Listener.class);
					return l1.weight() - l2.weight();
				}
			});

			if (async) {
				asyncListenerMap.put(action, list);
			} else {
				listenerMap.put(action, list);
			}
		}
		
		//if (getConstants().getDevMode()) {
			//System.out.println(String.format("listener[%s]-->>registered.", listener));
		//}

	}

	private EventListener newListener(Class<? extends EventListener> listenerClass) {
		EventListener listener = null;
		try {
			listener = listenerClass.newInstance();
		} catch (Throwable e) {
			System.err.println(String.format("listener \"%s\" newInstance is error. ", listenerClass));
			e.printStackTrace();
		}
		return listener;
	}

	private boolean listenerHasRegisterBefore(Class<? extends EventListener> listenerClass) {
		 return findFromMap(listenerClass, listenerMap)
	                || findFromMap(listenerClass, asyncListenerMap);
	}
	
	private boolean findFromMap(Class<? extends EventListener> listenerClass, Map<String, List<EventListener>> map) {
        for (Map.Entry<String, List<EventListener>> entry : map.entrySet()) {
            List<EventListener> listeners = entry.getValue();
            if (listeners == null || listeners.isEmpty()) {
                continue;
            }
            for (EventListener ml : listeners) {
                if (listenerClass == ml.getClass()) {
                    return true;
                }
            }
        }
        return false;
    }

	public void pulish(final Event message) {
		String action = message.getAction();

		List<EventListener> syncListeners = listenerMap.get(action);
		if (syncListeners != null && !syncListeners.isEmpty()) {
			invokeListeners(message, syncListeners);
		}

		List<EventListener> listeners = asyncListenerMap.get(action);
		if (listeners != null && !listeners.isEmpty()) {
			invokeListenersAsync(message, listeners);
		}

	}

	private void invokeListeners(final Event message, List<EventListener> syncListeners) {
		for (final EventListener listener : syncListeners) {
			try {
				//if (getConstants().getDevMode()) {
					//System.out.println(String.format("listener[%s]-->>onMessage(%s)", listener, message));
				//}
				listener.onEvent(message);
			} catch (Throwable e) {
				System.err.println(String.format("listener[%s] onMessage is erro! ", listener.getClass()));
				e.printStackTrace();
			}
		}
	}

	private void invokeListenersAsync(final Event message, List<EventListener> listeners) {
		for (final EventListener listener : listeners) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						//if (getConstants().getDevMode()) {
							//System.out.println(String.format("listener[%s]-->>onMessage(%s) in async", listener, message));
						//}
						listener.onEvent(message);
					} catch (Throwable e) {
						System.err.println(String.format("listener[%s] onMessage is erro! ", listener.getClass()));
						e.printStackTrace();
					}
				}
			});
		}
	}

}
