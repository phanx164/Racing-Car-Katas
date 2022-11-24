package tddmicroexercises.tirepressuremonitoringsystem

class Alarm(
  private val sensor: ISensor,
  private val threshold: ClosedFloatingPointRange<Double> = (17.0)..(21.0)
) {

  var isAlarmOn = false
    internal set

  fun check() {
    val psiPressureValue = sensor.popNextPressurePsiValue()

    if (!threshold.contains(psiPressureValue)) {
      isAlarmOn = true
    }
  }
}
