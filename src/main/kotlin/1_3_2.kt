fun main(args: Array<String>) {

    println("Введите сумму перевода:")
    println("Введите рубли:")
    val rub = readln()
    println("Введите копейки:")
    val kop = readln()
    println("Введите тип карты: (1, 2, 3)")
    println("1. MasterCard и Maestro")
    println("2. Visa и МИР")
    println("3. VK Pay")
    val typeRead = readln()
    println("Введите сумму предыдущих покупок за месяц в рублях:")
    val sumMonthRead = readln()
    var amount_ru = 0
    var amount_k = 0
    var amount = 0
    var sumMonth = 0
    var type = 0

    try {
        amount_ru = rub.toInt()
        amount_k = kop.toInt()
        type = typeRead.toInt()
        val sumMonth_k = sumMonthRead.toInt()
        sumMonth = sumMonth_k * 100
        amount = amount_ru * 100 + amount_k
        print("Сумма желаемого перевода равна:")
        println("  " + amount_ru + " руб. " + amount_k + " коп.")
        println("______________________________________________________________")
    } catch (e: Exception) {
        println("Вы ввели не число")
    }
    if ((amount > 150_000_00) || (sumMonth > 600_000_00)) {
        println("Превышен Ваш лимит 150000 рублей в сутки или 600000 рублей в месяц")
    } else commission(type, sumMonth, amount)

}

fun commission(
    type: Int,
    sumMonth: Int,
    amount: Int
): Double {
    return when (type) {
        1 -> {
            mastercard(amount)
        }
        2 -> {
            visa(amount)
        }
        3 -> {
            vk(amount, sumMonth)
        }
        else -> {
            println("Ошибка перевода!")
            0.0
        }
    }
}

private fun mastercard(amount: Int) = if (amount < 75000_00) {
    val perevod = amount / 100
    val procent = 0
    println("Всего списано за перевод: ${Math.round(perevod * 100.0) / 100.0}")
    println("Сумма комиссии за перевод: ${Math.round(procent * 100.0) / 100.0}")
    procent.toDouble()
} else {
    val procent = ((amount * (0.6 / 100)) + 20_00)
    val perevod = (amount + (procent)) / 100
    val procent1 = procent / 100
    println("Всего списано за перевод: ${Math.round(perevod * 100.0) / 100.0}")
    println("Сумма комиссии за перевод: ${Math.round(procent1 * 100.0) / 100.0}")
    procent
}

private fun visa(amount: Int) = if ((35_00 < amount) && (amount < 150_000_00)) {
    var procent = (amount * 0.75 / 100)
    if (procent < 35_00) {
        procent = 35_00.0
    }
    val perevod = (amount + (procent)) / 100
    val procent1 = procent / 100
    println("Всего списано за перевод: ${Math.round(perevod * 100.0) / 100.0}")
    println("Сумма комиссии за перевод: ${Math.round(procent1 * 100.0) / 100.0}")
    procent

} else {
    println("Введенная сумма меньше 35 руб или более 150000 руб. перевод невозможен")
    0.0
}

private fun vk(amount: Int, sumMonth: Int) = if ((amount > 15000_00) || (amount > sumMonth)) {
    println("Превышен лимит для VK Pay. Максимальная сумма 15000 руб. за один раз и не боллее 40000 руб. в месяц.")
    0.0
} else {
    val perevod = amount / 100
    val procent = 0.0
    println("Всего списано за перевод: ${Math.round(perevod * 100.0) / 100.0}")
    println("Сумма комиссии за перевод: ${Math.round(procent * 100.0) / 100.0}")
    procent
}





