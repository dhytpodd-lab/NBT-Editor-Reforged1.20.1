package net.fabricmc.fabric.api.event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class EventFactory {

	private EventFactory() {
	}

	public static <T> Event<T> createArrayBacked(Class<T> type, Function<T[], T> invokerFactory) {
		return new ArrayBackedEvent<>(type, invokerFactory);
	}

	private static final class ArrayBackedEvent<T> implements Event<T> {
		private final Class<T> type;
		private final Function<T[], T> invokerFactory;
		private final List<T> listeners = new ArrayList<>();
		private volatile T invoker;

		private ArrayBackedEvent(Class<T> type, Function<T[], T> invokerFactory) {
			this.type = type;
			this.invokerFactory = invokerFactory;
			this.invoker = invokerFactory.apply(newArray(0));
		}

		@Override
		public synchronized void register(T listener) {
			listeners.add(listener);
			invoker = invokerFactory.apply(listeners.toArray(newArray(listeners.size())));
		}

		@Override
		public T invoker() {
			return invoker;
		}

		@SuppressWarnings("unchecked")
		private T[] newArray(int size) {
			return (T[]) Array.newInstance(type, size);
		}
	}

}
