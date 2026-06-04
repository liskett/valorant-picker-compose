package com.example.valorantpickercompose.presentation.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.valorantpickercompose.R
import com.example.valorantpickercompose.domain.model.SettingType
import com.example.valorantpickercompose.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBackClick: () -> Unit
) {
    val currentSetting by viewModel.settingType.collectAsStateWithLifecycle()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.settings_title),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(24.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.recommendation_priority),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.recommendation_description),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Вариант 1: Win Rate Priority
                SettingsOption(
                    titleResId = R.string.win_rate_priority,
                    descriptionResId = R.string.win_rate_priority_description,
                    isSelected = currentSetting == SettingType.WIN_RATE_PRIORITY,
                    onClick = { viewModel.saveSetting(SettingType.WIN_RATE_PRIORITY) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Вариант 2: Pick Rate Priority
                SettingsOption(
                    titleResId = R.string.pick_rate_priority,
                    descriptionResId = R.string.pick_rate_priority_description,
                    isSelected = currentSetting == SettingType.PICK_RATE_PRIORITY,
                    onClick = { viewModel.saveSetting(SettingType.PICK_RATE_PRIORITY) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Вариант 3: Balanced
                SettingsOption(
                    titleResId = R.string.balanced_recommended,
                    descriptionResId = R.string.balanced_description,
                    isSelected = currentSetting == SettingType.BALANCED,
                    onClick = { viewModel.saveSetting(SettingType.BALANCED) }
                )
            }

            // Кнопка "Назад"
            Icon(
                painter = painterResource(R.drawable.lucide_arrow_big_left),
                contentDescription = stringResource(R.string.back_content_description),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 16.dp)
                    .clickable { onBackClick() }
            )
        }
    }
}

@Composable
private fun SettingsOption(
    titleResId: Int,
    descriptionResId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(titleResId),
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(descriptionResId),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}