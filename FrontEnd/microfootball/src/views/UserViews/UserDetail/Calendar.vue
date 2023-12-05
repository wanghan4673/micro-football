<!-- 签到日历 -->
<template>
    <div class="calendar-box">
        <a-config-provider :locale="zhCN">
            <a-calendar v-model:value="value" :fullscreen="false" @panelChange="onPanelChange" />
      </a-config-provider>
    </div>
</template>

<script setup lang="ts">
import { ElMessage } from 'element-plus'
import axios from 'axios'
import { ref } from 'vue'
import { Dayjs } from 'dayjs'
import zhCN from 'ant-design-vue/es/locale/zh_CN'
import dayjs from 'dayjs'
import 'dayjs/locale/zh-cn'

dayjs.locale('zh-cn')

const value = ref<Dayjs>();
const onPanelChange = (value: Dayjs, mode: string) => {
  console.log(value, mode);
};
</script>

<style lang="scss">
</style>