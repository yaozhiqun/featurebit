package com.lendingclub.featurebit

import org.scalatest.{Matchers, FreeSpec, FunSuite}

/**
 * Created by Zachary Yao on 3/19/17.
 */
class FeatureBitImplTest extends FreeSpec with Matchers {

  "Feature bit" - {
    "Should return true if enabled" in {
      FeatureBit.loanTreasure shouldBe true
    }

    "Should return false if disabled" in {
      FeatureBit.loanHouse shouldBe false
    }

    "Should return false if enabled feature is overridden to disabled" in {
      FeatureBit.withFeaturesDisabled(FeatureLoanTreasure) {
        FeatureBit.loanTreasure shouldBe false
      }
    }

    "Should return true if disabled feature is overridden to enabled" in {
      FeatureBit.withFeaturesEnabled(FeatureLoanHouse) {
        FeatureBit.loanHouse shouldBe true
      }
    }
  }
}
