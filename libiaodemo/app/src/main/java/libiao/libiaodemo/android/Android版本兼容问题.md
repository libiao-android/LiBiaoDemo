1、
Wrong state class, expecting View State but received class android.widget.ProgressBar$SavedState instead.
This usually happens when two views of different type have the same id in the same hierarchy.
This view's id is id/a_k. Make sure other views do not use the same id

那么解决这个问题的根本解决方法就是检查一遍从根布局开始的所有的id是否存在相同吧。不要侥幸高版本的机型没有出现问题。
https://www.jianshu.com/p/59ca8b3ce9e8