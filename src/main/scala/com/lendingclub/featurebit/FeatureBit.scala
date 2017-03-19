package com.lendingclub.featurebit

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.mutable

/**
 * Created by Zachary Yao on 3/19/17.
 */
trait FeatureBit {

  def enable(feature: Feature): Unit

  def disable(feature: Feature): Unit

  def withFeaturesEnabled[T](features: Feature*)(f: => T): T

  def withFeaturesDisabled[T](features: Feature*)(f: => T): T

  def all: Seq[Feature]

}

class FeatureBitImpl extends FeatureBit {

  private val config: Config = ConfigFactory.parseResources("application.features")
  
  private val overrides = mutable.HashMap[String, Feature]()

  private[featurebit] def featureEnabled(feature: Feature): Boolean = {
    overrides.get(feature.name).map(_.enabled).getOrElse {
      val featureName = s"feature.${feature.name}"
      config.getBoolean(featureName)
    }
  }

  override def enable(feature: Feature): Unit = {
    switch(feature, enabled = true)
  }

  override def disable(feature: Feature): Unit = {
    switch(feature, enabled = false)
  }

  def withFeaturesEnabled[T](features: Feature*)(f: => T): T = {
    `override`(features, enabled = true)(f)
  }

  def withFeaturesDisabled[T](features: Feature*)(f: => T): T = {
    `override`(features, enabled = false)(f)
  }

  private def switch(feature: Feature, enabled: Boolean): Unit = {
    overrides.put(feature.name, feature.copy(enabled = enabled))
  }
  
  private def `override`[T](features: Seq[Feature], enabled: Boolean)(f: => T): T = {
    try {
      features.foreach(switch(_, enabled))
      f
    } finally {
      features.foreach(unset)
    }
  }

  def unset(feature: Feature) = overrides.remove(feature.name)
  
  override def all: Seq[Feature] = {
    overrides.values.toSeq
  }
}

object FeatureBit extends FeatureBitImpl {
  def loanTreasure: Boolean = featureEnabled(FeatureLoanTreasure)
  def loanHouse: Boolean = featureEnabled(FeatureLoanHouse)
}