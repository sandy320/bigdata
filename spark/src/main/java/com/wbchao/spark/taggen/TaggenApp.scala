package com.wbchao.spark.taggen

import com.wbchao.spark.utils.JsonUtils
import org.apache.spark.{SparkConf, SparkContext}

object TaggenApp {
  def main(args: Array[String]): Unit = {
    println("Start taggen App")
    val path = "file:///D:/IdeaProjects/big-data/spark/src/main/resources/"
    val conf = new SparkConf()
    conf.setAppName("WordCount")
    conf.setMaster("local")

    val sc = new SparkContext(conf)
    val rdd = sc.textFile(path + "tempTags.txt")

    val rdd1 = rdd.map(t => {
      val arr = t.split("\t")
      (arr(0), arr(1))
    }).filter(t => t._2.size > 0)

    val rdd2 = rdd1.map(t => (t._1, JsonUtils.getTags(t._2))).filter(t => t._2.size > 0)

    val rdd3 = rdd2.flatMapValues(t => t)

    val rdd4 = rdd3.map(t => (t,1))

    val rdd5 = rdd4.reduceByKey(_+_)

    val rdd6 = rdd5.map(t => (t._1._1, (t._1._2, t._2)))

    val rdd7 = rdd6.groupByKey()

    val rdd8 = rdd7.map(t => {
      val bussId = t._1
      val list = t._2
      (bussId,list.toList.sortBy(t => t._2).reverse)
    })

    val rdd9 = rdd8.sortBy(t => -t._2(0)._2)

    rdd9.foreach(println)
  }
}
