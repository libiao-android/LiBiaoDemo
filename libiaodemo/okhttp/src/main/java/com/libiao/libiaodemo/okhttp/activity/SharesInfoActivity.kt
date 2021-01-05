package com.libiao.libiaodemo.okhttp.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.libiao.libiaodemo.okhttp.R
import okhttp3.*
import java.io.IOException
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class SharesInfoActivity : AppCompatActivity() {

    private var mSharesInfoRv: RecyclerView? = null
    private var mAdapter: SharesInfoAdater? = null
    private var mSharesInfoList: ArrayList<SharesInfo> = ArrayList()
    private val mHandler = Handler()

    private var mUpTv: TextView? = null
    private var mUpIv: ImageView? = null
    private var sortType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shares_info_activity)
        supportActionBar?.hide()

        mUpTv = findViewById(R.id.tv_up)
        mUpIv = findViewById(R.id.iv_up)

        mSharesInfoRv = findViewById(R.id.shares_info_rv)
        mSharesInfoRv?.layoutManager = LinearLayoutManager(this)
        mAdapter = SharesInfoAdater(this)
        mSharesInfoRv?.adapter = mAdapter

        initData()

        mUpTv?.setOnClickListener {
            when (sortType) {
                0 -> {
                    sortType = 1
                    mUpIv?.setImageResource(R.mipmap.sort_ascending)
                    //升序
                    mSharesInfoList.sortWith(kotlin.Comparator { o1, o2 ->
                        val price1 = (o1.down + o1.up)/2
                        val price2 = (o2.down + o2.up)/2
                        price1.compareTo(price2)
                    })
                }
                1 -> {
                    sortType = 2
                    mUpIv?.setImageResource(R.mipmap.sort_descending)
                    //降序
                    mSharesInfoList.sortWith(kotlin.Comparator { o1, o2 ->
                        val price1 = (o1.down + o1.up)/2
                        val price2 = (o2.down + o2.up)/2
                        price2.compareTo(price1)
                    })
                }
                else -> {
                    sortType = 1
                    mUpIv?.setImageResource(R.mipmap.sort_ascending)
                    //升序
                    mSharesInfoList.sortWith(kotlin.Comparator { o1, o2 ->
                        val price1 = (o1.down + o1.up)/2
                        val price2 = (o2.down + o2.up)/2
                        price1.compareTo(price2)
                    })
                }
            }
            mAdapter?.setData(mSharesInfoList)
        }
    }

    private fun initData() {

        mSharesInfoList.add(SharesInfo("今世缘", "sh603369", 43, 43))
        mSharesInfoList.add(SharesInfo("五粮液", "sz000858", 180, 180))
        mSharesInfoList.add(SharesInfo("分众传媒", "sz002027", 8, 9))
        mSharesInfoList.add(SharesInfo("北新建材", "sz000786", 31, 35))
        mSharesInfoList.add(SharesInfo("东方雨虹", "sz002271", 30, 33))

        mSharesInfoList.add(SharesInfo("中国中免", "sh601888", 160, 160))
        mSharesInfoList.add(SharesInfo("宁德时代", "sz300750", 180, 180))
        mSharesInfoList.add(SharesInfo("恩捷股份", "sz002812", 70, 70))
        mSharesInfoList.add(SharesInfo("璞泰来", "sh603659", 82, 82))
        mSharesInfoList.add(SharesInfo("宏发股份", "sh600885", 34, 40))

        mSharesInfoList.add(SharesInfo("隆基股份", "sh601012", 55, 55))
        mSharesInfoList.add(SharesInfo("通威股份", "sh600438", 26, 26))
        mSharesInfoList.add(SharesInfo("恒瑞医药", "sh600276", 80, 90))
        mSharesInfoList.add(SharesInfo("长春高新", "sz000661", 330, 330))
        mSharesInfoList.add(SharesInfo("三七互娱", "sz002555", 0, 0))

        mSharesInfoList.add(SharesInfo("移远通信", "sh603236", 160, 180))
        mSharesInfoList.add(SharesInfo("三安光电", "sh600703", 22, 24))
        mSharesInfoList.add(SharesInfo("歌尔股份", "sz002241", 37, 37))
        mSharesInfoList.add(SharesInfo("立讯精密", "sz002475", 45, 45))
        mSharesInfoList.add(SharesInfo("TCL科技", "sz000100", 6, 6))

        mSharesInfoList.add(SharesInfo("宝信软件", "sh600845", 55, 55))
        mSharesInfoList.add(SharesInfo("广联达", "sz002410", 50, 50))
        mSharesInfoList.add(SharesInfo("用友网络", "sh600588", 30, 37))
        mSharesInfoList.add(SharesInfo("恒生电子", "sh600570", 90, 90))
        mSharesInfoList.add(SharesInfo("保利地产", "sh600048", 16, 16))

        mSharesInfoList.add(SharesInfo("东方财富", "sz300059", 16, 18))
        mSharesInfoList.add(SharesInfo("宁波银行", "sz002142", 27, 27))
        mSharesInfoList.add(SharesInfo("招商银行", "sh600036", 34, 34))

        val sb = StringBuilder()

        for(info in mSharesInfoList) {
            sb.append(info.code)
            sb.append(",")
        }


        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://hq.sinajs.cn/list=$sb")
                .build()

        val call = client.newCall(request)

        call.enqueue(object : Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                Log.i("libiao", "onFailure: ${e}")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val value = response?.body()?.string()
                //Log.i("libiao", "response: ${value}")
                mHandler.post { parseSharesInfo(value) }

            }
        })
    }

    private fun parseSharesInfo(response: String?) {
        response?.also {
            val data = it.replace("\n", "")
            val sharesInfos = data.split(";")
            for(str in sharesInfos) {
                val leftRight = str.split("=")
                if(leftRight.size < 2) continue
                val info = leftRight[0].split("_")
                val code = info[info.size-1]
                val params = leftRight[1].split(",")
                val name = params[0]
                val price = params[3]
                Log.i("libiao", "code: $code, name: $name, price: $price")

                val sharesInfo = getSharesInfo(code)
                sharesInfo?.also {item ->
                    item.price = price.toFloat()
                    item.down = (item.price - item.valuationMin) / item.valuationMin
                    item.up = (item.price - item.valuationMax) / item.valuationMax
                }
            }
            mAdapter?.setData(mSharesInfoList)
        }
    }

    private fun getSharesInfo(code: String): SharesInfo? {
        for(info in mSharesInfoList) {
            if(info.code == code) return info
        }
        return null
    }

    class SharesInfoAdater(val context: Context) : RecyclerView.Adapter<SharesInfoHolder>() {

        private var mSharesInfoList: ArrayList<SharesInfo> = ArrayList()

        fun setData(sharesInfoList: ArrayList<SharesInfo>) {
            mSharesInfoList = sharesInfoList
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharesInfoHolder {
            return SharesInfoHolder(LayoutInflater.from(context).inflate(R.layout.shares_info_item, null))
        }

        override fun getItemCount(): Int {
            return mSharesInfoList.size
        }

        override fun onBindViewHolder(holder: SharesInfoHolder, position: Int) {
            holder.bindData(mSharesInfoList.get(position))
        }
    }

    class SharesInfoHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var mTitleTv: TextView? = null
        private var mValuationTv: TextView? = null
        private var mPriceTv: TextView? = null
        private var mUpTv: TextView? = null

        init {
            mTitleTv = view.findViewById(R.id.tv_title_item)
            mValuationTv = view.findViewById(R.id.tv_valuation_item)
            mPriceTv = view.findViewById(R.id.tv_price_item)
            mUpTv = view.findViewById(R.id.tv_up_item)
        }

        fun bindData(info: SharesInfo) {
            mTitleTv?.text = info.name
            when(info.valuationMin) {
                0 -> {
                    mValuationTv?.text = "--"
                    mUpTv?.text = "--"
                }
                info.valuationMax -> {
                    mValuationTv?.text = "${info.valuationMin}"
                    if(info.down < 0) {
                        mUpTv?.setTextColor(Color.RED)
                    } else {
                        mUpTv?.setTextColor(Color.BLACK)
                    }
                    mUpTv?.text = String.format("%.2f",(info.down*100))
                }
                else ->{
                    mValuationTv?.text = "${info.valuationMin}-${info.valuationMax}"
                    if((info.up + info.down)/2 < 0) {
                        mUpTv?.setTextColor(Color.RED)
                    } else {
                        mUpTv?.setTextColor(Color.BLACK)
                    }
                    mUpTv?.text = "${String.format("%.2f",(info.down*100))}-${String.format("%.2f",(info.up*100))}"
                }
            }
            mPriceTv?.text = info.price.toString()
        }

    }

    class SharesInfo {
        var name: String? = null //股票名称
        var code: String? = null //股票代码
        var valuationMin: Int = 0 //合理估值区间
        var valuationMax: Int = 0 //合理估值区间
        var price: Float = 0f //当前价格
        var up: Float = 0f //对比合理估值区间的涨跌幅
        var down: Float = 0f //对比合理估值区间的涨跌幅

        constructor() {}
        constructor(name: String, code: String, valuationMin: Int, valuationMax: Int) {
            this.name = name
            this.code = code
            this.valuationMin = valuationMin
            this.valuationMax = valuationMax
        }
    }
}

//新浪的股票数据接口提供个股的最新行情。
//例如：要获取平安银行（000001）的最新行情，只需访问http://hq.sinajs.cn/list=sz000001，这个url会返回一串文本：
//var hq_str_sz000001="平安银行,9.170,9.190,9.060,9.180,9.050,9.060,9.070,42148125,384081266.460,624253,9.060,638540,9.050,210600,9.040,341700,9.030,2298300,9.020,227184,9.070,178200,9.080,188240,9.090,293536,9.100,295300,9.110,2016-09-14,15:11:03,00";
//这个字符串由许多数据拼接在一起，不同含义的数据用逗号隔开了，按照程序员的思路，顺序号从0开始。
//0：“平安银行”，股票名字；
//1：“9.170”，今日开盘价；
//2：“9.190”，昨日收盘价；
//3：“9.060”，当前价格；
//4：“9.180”，今日最高价；
//5：“9.050”，今日最低价；
//6：“9.060”，竞买价，即“买一“报价；
//7：“9.070”，竞卖价，即“卖一“报价；
//8：“42148125”，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
//9：“384081266.460”，成交金额，单位为“元“，为了一目了然，通常以“万元“为成交金额的单位，所以通常把该值除以一万；
//10：“624253”，“买一”申请624253股，即6243手；
//11：“9.060”，“买一”报价；
//12：“638540”，“买二”申报股数；
//13：“9.050”，“买二”报价；
//14：“210600”，“买三”申报股数；
//15：“9.040”，“买三”报价；
//16：“341700”，“买四”申报股数；
//17：“9.030”，“买四”报价；
//18：“2298300”，“买五”申报股数；
//19：“9.020”，“买五”报价；
//20：“227184”，“卖一”申报227184股，即2272手；
//21：“9.070”，“卖一”报价；
//(22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖五”的申报股数及其价格；
//30：“2016-09-14”，日期；
//31：“15:11:03”，时间；