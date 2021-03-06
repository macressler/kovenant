/*
 * Copyright (c) 2015 Mark Platvoet<mplatvoet@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * THE SOFTWARE.
 */

package nl.mplatvoet.komponents.kovenant

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.properties.ReadWriteProperty


[suppress("UNCHECKED_CAST")]
public class ThreadSafeLazyVar<T>(private val initializer: () -> T) : ReadWriteProperty<Any?, T> {
    private val lock = ReentrantReadWriteLock()
    private volatile var value: Any? = null


    public override fun get(thisRef: Any?, desc: PropertyMetadata): T {
        if (value == null) {
            try {
                lock.writeLock().lock()
                if (value == null) {
                    value = mask(initializer())
                }
            } finally {
                lock.writeLock().unlock()
            }
        }
        return unmask(value) as T
    }

    public override fun set(thisRef: Any?, desc: PropertyMetadata, value: T) {
        this.value = mask(value)
    }

    val initialized: Boolean get() = value != null
}

[suppress("UNCHECKED_CAST")]
private class TrackChangesVar<T>(private val source: () -> T) : ReadWriteProperty<Any?, T> {
    private volatile var value: Any? = null

    public override fun get(thisRef: Any?, desc: PropertyMetadata): T {
        val curVal = value
        return if (curVal != null) unmask(curVal) as T else source()
    }

    public override fun set(thisRef: Any?, desc: PropertyMetadata, value: T) {
        this.value = mask(value)
    }

    val written: Boolean get() = value != null
}

private val NULL_VALUE: Any = Any()
private fun mask(value: Any?): Any = value ?: NULL_VALUE
private fun unmask(value: Any?): Any? = if (value == NULL_VALUE) null else value
