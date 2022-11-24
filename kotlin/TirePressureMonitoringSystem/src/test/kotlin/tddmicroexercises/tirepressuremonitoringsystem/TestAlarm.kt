package tddmicroexercises.tirepressuremonitoringsystem

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.absoluteValue
import kotlin.random.Random

class TestAlarm {

  @Test
  fun foo() {
    val alarm = Alarm(Sensor())
    assertEquals(false, alarm.isAlarmOn)
  }

  @Test
  fun `with sensor return low pressure`(){
    val alarm = Alarm(OnlyReturnLowPressureSensor(17.9), threshold = 18.0..20.0)
    for(i in 1..10) {
      alarm.check()
      assertTrue(alarm.isAlarmOn)
    }
  }
  @Test
  fun `with sensor return high pressure`(){
    val alarm = Alarm(OnlyReturnHighPressureSensor(20.1), threshold = 18.0..20.0)
    for(i in 1..10) {
      alarm.check()
      assertTrue(alarm.isAlarmOn)
    }
  }
  @Test
  fun `with sensor return good pressure`(){
    val alarm = Alarm(WithinRangePressureSensor(18.0..20.0), threshold = 18.0..20.0)
    for(i in 1..10) {
      alarm.check()
      assertFalse(alarm.isAlarmOn)
    }
  }
}

class OnlyReturnLowPressureSensor(private val highest: Double): ISensor{
  override fun popNextPressurePsiValue(): Double {
    return Random.nextDouble(highest)
  }
}

class WithinRangePressureSensor(private val range: ClosedFloatingPointRange<Double>): ISensor{
  override fun popNextPressurePsiValue(): Double {
    return Random.nextDouble(range.start, range.endInclusive)
  }
}

class OnlyReturnHighPressureSensor(private val lowest: Double): ISensor{
  override fun popNextPressurePsiValue(): Double {
    return lowest + Random.nextDouble().absoluteValue
  }
}
