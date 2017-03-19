package com.lendingclub.featurebit

/**
 * Created by Zachary Yao on 3/19/17.
 */
case class Feature(name: String, enabled: Boolean = false)

object FeatureLoanTreasure extends Feature("loan.treasure")
object FeatureLoanHouse extends Feature("loan.house")