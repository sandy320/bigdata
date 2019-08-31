package com.wbchao.spark.utils

import net.minidev.json.{JSONArray, JSONObject}
import net.minidev.json.parser.JSONParser

object JsonUtils {
  def main(args: Array[String]): Unit = {
    val text = "{\"reviewPics\":null,\"extInfoList\":[{\"title\":\"contentTags\",\"values\":[\"音响效果好\",\"干净卫生\",\"环境优雅\"],\"desc\":\"\",\"defineType\":0},{\"title\":\"tagIds\",\"values\":[\"173\",\"852\",\"24\"],\"desc\":\"\",\"defineType\":0}],\"expenseList\":null,\"reviewIndexes\":[2],\"scoreList\":null}"
    val tags = getTags(text)
    println(tags)
    println(tags.getClass)
  }

  def getTags(text: String) = {
    val jsonParser = new JSONParser()
    val jsonObj: JSONObject = jsonParser.parse(text).asInstanceOf[JSONObject]
    val extInfoList: JSONArray = jsonObj.get("extInfoList").asInstanceOf[JSONArray]
    if (extInfoList != null && extInfoList.size() > 0) {
      val extInfo: JSONObject = extInfoList.get(0).asInstanceOf[JSONObject]
      val tags: JSONArray = extInfo.get("values").asInstanceOf[JSONArray]
      tags.toArray.toList
    } else {
      Nil
    }
  }
}
