package com.diplom.mediresult.presentation.onboarding.components

import androidx.annotation.DrawableRes
import com.diplom.mediresult.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages =listOf(
    Page(
        title = "Анализы",
        description = "Экспресс сбор и получение проб",
        image = R.drawable.onboarding_img1
    ),
    Page(
        title = "Уведомления",
        description = "Вы быстро узнаете о результатах",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Мониторинг",
        description = "Наши врачи всегда соблюдают\n \tза вашими покозателями",
        image = R.drawable.onboarding3
    ),
)
