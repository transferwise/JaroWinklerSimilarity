package de.uni_jena.cs.fusion.similarity.jarowinkler;

/*-
 * #%L
 * Jaro Winkler Similarity
 * %%
 * Copyright (C) 2018 - 2023 Heinz Nixdorf Chair for Distributed Information Systems, Friedrich Schiller University Jena
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.ArrayDeque;

/**
 * Non thread-safe implementation of a boolean array pool.
 * <p></p>
 * The pool is supposed to reduce object allocation, therefore, reduce the load on GC.
 */
public class BooleanArrayPool {
    private final ArrayDeque<boolean[]> pool;
    private final int arraySize;

    BooleanArrayPool(int capacity, int arraySize) {
        this.pool = new ArrayDeque<>(capacity);
        this.arraySize = arraySize;
    }

    BooleanArrayPool(int capacity, int arraySize, boolean enable) {
        this.pool = new ArrayDeque<>(capacity);
        this.arraySize = arraySize;
    }

    /**
     * Acquire a dirty array
     * @return boolean array of {@link #arraySize} size.
     */
    public boolean[] acquireDirty() {
        boolean[] array = pool.poll();
        if (array != null) {
            return array;
        }
        return new boolean[arraySize];
    }

    /**
     * Back an acquired array to the pool.
     * @param array the array that have to be reduced to the pool.
     */
    public void release(boolean[] array) {
        pool.offer(array);
    }
}
