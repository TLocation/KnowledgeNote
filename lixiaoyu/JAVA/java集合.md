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

Collection 接口：



Map 接口：



Iterator 迭代：



工具类：

​	Collections：

​	Arrays：



比较器：

​	Comparable  Comparator





###### ArrayList

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
> 

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



#### add方法源码分析

```
public boolean add(E e) {
		//确定容量  动态扩容  size初始0 
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        //将 要添加的元素添加到数组中   
        elementData[size++] = e;
        return true;
    }
```

```
private void ensureCapacityInternal(int minCapacity) {
		// 如果等于 空
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        	// mincapacity 就取 下面两个值的最大值  
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }
```

```
private void ensureExplicitCapacity(int minCapacity) {
        modCount++;  //自增长  操作次数

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }
```

```
private void grow(int minCapacity) {  // 10
        // overflow-conscious code
        int oldCapacity = elementData.length;//0
        int newCapacity = oldCapacity + (oldCapacity >> 1);//0+0
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 返回一个新的数组  
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```

