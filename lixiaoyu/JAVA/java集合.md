# 数据结构

## 数组

> ```
> char[] ch = new char[] {'l','x','y','a','n'};
> System.out.println(ch[5]);
> 
> //会出现 索引越界异常本来就5个，下标最大是4
> Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 5
> ```
>
> 特点：
>
> ​	1.内存地址连续，使用之前必须要制定数组长度。
>
> ​	2.可以通过下标访问的方式访问成员，查询效率比较高。
>
> ​	3.增删操作会带来性能消耗。(如果想在一个数组上再加一个数据，那么就得继续扩容：数组的扩容就是重新创建一个下标是5的数组 把原来的拿过来 再在最后面添加上第六个)



## 链表

###### 双向链表

> ```
> LinkedList 源码 中的内部类
> 
> private static class Node<E> {
>         E item; //节点元素
>         Node<E> next;//下一个节点
>         Node<E> prev;//上一个节点
> 
>         Node(Node<E> prev, E element, Node<E> next) {
>             this.item = element;
>             this.next = next;
>             this.prev = prev;
>         }
>     }
> ```
>
> 特点:
>
> ​	1.灵活的空间要求，存储空间不要求连续。
>
> ​	2.不支持下标访问，支持顺序遍历检索。
>
> ​	3.针对增删效果更高一些，只和操作节点的前后节点有关系，无需移动元素。



## 树

###### 红黑树

> 红黑树Red-Black-Tree[RBT] 是一个**自平衡**【不是绝对的】的二叉树。树上的节点需满足如下规则。
>
> 1.每个节点要么是红色，要么是黑色。
>
> 2.根节点必须是黑色。
>
> 3.每个叶子节点【NIL】是黑色。
>
> 4.每个红色节点的两个子节点必须是黑色。
>
> 5.任意节点到每个叶子节点的路劲包含相同数量的黑节点。
>
> 





## 集合

###### Collection 接口：



###### Map 接口：



###### Iterator 迭代：



###### 工具类：

​	Collections：

​	Arrays：



比较器：

​	Comparable  Comparator



### ArrayList 

###### 本质就是动态数组,动态扩容

```
	/**
     * Default initial capacity.
     //默认的数组长度
     */
	private static final int DEFAULT_CAPACITY = 10;
	

    /**
     * Shared empty array instance used for empty instances.
     //空数组
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     //集合中存储数据的  数组对象
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer. Any
     * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * will be expanded to DEFAULT_CAPACITY when the first element is added.
     */
    transient Object[] elementData; // non-private to simplify nested class access

    /**
     * The size of the ArrayList (the number of elements it contains).
     *
     * @serial
     //集合中元素的个数
     */
    private int size;
```

#### 初始化操作

> 无参构造
>
> ```
>     public ArrayList() {
>         this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
>     }
> ```
>

##### add添加源码分析

###### 第一次添加

```
public boolean add(E e) {
		//确定容量  动态扩容  size初始0 
        ensureCapacityInternal(size + 1);  // ensureCapacityInternal(1);
        //将 要添加的元素添加到数组中   
        elementData[size++] = e;
        return true;
    }
```

```
private void ensureCapacityInternal(int minCapacity) { //1
		//ensureExplicitCapacity(10)
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
 }
 
//elementData {} 为空    mincapacity为1
private static int calculateCapacity(Object[] elementData, int minCapacity) {
        //如果elementdata == {}空
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        	//DEFAULT_CAPACITY 默认10
        	//返回下面2个参数的最大值
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        //不等于空返回
        return minCapacity;
 }

```

```
private void ensureExplicitCapacity(int minCapacity) { //10
        modCount++; // 自增长

        // 10 - 0
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }
```

```java
private void grow(int minCapacity) { // 10
        // oldCapacity 0
        int oldCapacity = elementData.length; // 0
    	//newCapacity 0
        int newCapacity = oldCapacity + (oldCapacity >> 1);// 向右移动一位
        // 0 - 10 
    	if (newCapacity - minCapacity < 0)
            //newCapacity 10
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 返回一个新的长度数组
    	//elementData {} 付给了 newCapacity {,,,,,,,,,}
        elementData = Arrays.copyOf(elementData, newCapacity);
}
```



###### 第二次添加

```java
public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // 2
        elementData[size++] = e;
        return true;
}
```

```java
private void ensureCapacityInternal(int minCapacity) { // 2
    	// ensureExplicitCapacity(10,2)
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
}

private static int calculateCapacity(Object[] elementData, int minCapacity) {
    	// 10 == {}
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
    	//无效返回 2
        return minCapacity;
    }
```

```java
private void ensureExplicitCapacity(int minCapacity) { //2
        modCount++;

        // 2 - 10 不成立 继续往下执行
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

```

```java
public boolean add(E e) { 
        ensureCapacityInternal(size + 1);  // 2
        elementData[size++] = e; // elementData[2] = e;
        return true;
    }
```



###### 第11次添加

```java
public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // 12
        elementData[size++] = e;
        return true;
    }

```

```java
private void ensureCapacityInternal(int minCapacity) { //12
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

//参数是     10 12
private static int calculateCapacity(Object[] elementData, int minCapacity) { 
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
    	//返回12
        return minCapacity;
    }
```

```java
private void ensureExplicitCapacity(int minCapacity) { //12
        modCount++;

        // 12 - 10  > 0
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

private void grow(int minCapacity) { //12
        // oldCapacity 10
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1); // 10 + 5
    	// 15 - 10
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
    	// MAX_ARRAY_SIZE 是指integer的最大值  这个地方始终都是小于0
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        //elementData 10 {1,2,3,4,5,6,7,8,9,10}
    	//newCapacity 15 {1,2,3,4,5,6,7,8,9,10,,,,,}
    	//也就是在原来的基础上复制到另一个新的数组中
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // 12
        elementData[size++] = e; elementData[12] = e;
        return true;
    }

```



##### get获取源码分析

```java
public E get(int index) { // 1
    	//检查下标是否合法
        rangeCheck(index);
		
    	//返回指定下标处的数据
        return elementData(index);
    }

private void rangeCheck(int index) {
        if (index >= size) // 下标如果大于 长度
            //就抛出异常索引越界
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
```



##### set获取源码分析

```java
// 下标为1     数据 11
public E set(int index, E element) { // 1   11
    //检查下标是否合法 否则抛出索引越界    
    rangeCheck(index);
		//获取到索引处数据
        E oldValue = elementData(index);
    	//把索引处数据进行赋值
        elementData[index] = element;
    	//返回原来的数据
        return oldValue;
    }
```



##### remove获取源码分析(数组长度不变)

```java
public E remove(int index) { // 1
        //检查索引是否合法
    	rangeCheck(index);

        modCount++;
    	//获取到索引处数据
        E oldValue = elementData(index);
		//15 - 1 - 1 = 13
        int numMoved = size - index - 1;
    	//13 > 0
        if (numMoved > 0)
            //源数组{1,2,3,4,5,6,7,8,9}
            //目标数组{1,3,4,5,6,7,8,9,null}
            //这边有一个这样的操作 就是覆盖掉了
            // 源数组  开始下标   目标数组 开始下标  长度
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // 把原来的数组 下标为 14的赋值为null
		//返回删除的数据
        return oldValue;
    }
```





> 有参构造
>
> ```
> public ArrayList(int initialCapacity) {
>         if (initialCapacity > 0) {
>         	//出事长度大于0 就创建制定大小的数组
>             this.elementData = new Object[initialCapacity];
>         } else if (initialCapacity == 0) {
>         	//否则就赋值一个空数组
>             this.elementData = EMPTY_ELEMENTDATA;
>         } else {
>         	//否则抛出异常
>             throw new IllegalArgumentException("Illegal Capacity: "+
>                                                initialCapacity);
>         }
>     }
> ```
>
> 



