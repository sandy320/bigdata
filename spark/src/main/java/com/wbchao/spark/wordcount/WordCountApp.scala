package com.wbchao.spark.wordcount

import org.apache.commons.configuration.Configuration
import org.apache.spark.{SparkConf, SparkContext}

object WordCountApp {
  def main(args: Array[String]): Unit = {
    println("Start word count App")
    val path = "file:///D:/IdeaProjects/big-data/spark/src/main/resources/"
    val conf = new SparkConf()
    conf.setAppName("WordCount")
    conf.setMaster("local")

    val sc = new SparkContext(conf)
    val rdd = sc.textFile(path + "1.txt")

    val rdd1 = rdd.flatMap(_.split(" "))
    val rdd2 = rdd1.map(t => t -> 1)
    val rdd3 = rdd2.reduceByKey(_+_)
    var result = rdd3.collect()

    result.foreach(println)
  }
}
