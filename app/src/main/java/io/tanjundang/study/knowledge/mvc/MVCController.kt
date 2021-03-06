package io.tanjundang.study.knowledge.mvc

/**
 * @Author: TanJunDang
 * @Date: 2018/10/12
 * @Description: 控制model在view上的显示，以及响应用户的交互。因此会有两者的引用
 * 这里为了让Activity更加解耦，特意抽出Controller出来。一般Activity是可以充当Controller角色的，但是那样会使Activity代码臃肿。
 */
class MVCController(internal var activity: MVCActivity) {

    internal var model: MVCModel? = null

    fun loadData() {
        model = MVCModel(activity)
        model!!.loadData()
    }
}
