<template>
  <div class="calendar-box">
    <a-config-provider :locale="zhCN">
      <a-calendar v-model:value="value" :fullscreen="false">
        <template #dateCellRender="{ current }">
          <div class="date-container">
            <span v-if="isChecked(current)" class="check-mark">✔️</span>
          </div>
        </template>
      </a-calendar>
    </a-config-provider>
  </div>
  <div class="checkin-button-box">
    <el-button class="checkin-button" @click="checkIn">点此签到</el-button>
  </div>
</template>

<script setup lang="ts">
import { ElMessage } from 'element-plus'
import axios from 'axios'
import { ref, onMounted } from 'vue'
import { Dayjs } from 'dayjs'
import zhCN from 'ant-design-vue/es/locale/zh_CN'
import dayjs from 'dayjs'
import 'dayjs/locale/zh-cn'
dayjs.locale('zh-cn')

const value = ref<Dayjs>()
const checkedDates = ref<string[]>([])  // 已签到的日期

onMounted(() => {
  getCheckDays()
})

const getCheckDays = async () => {
  const token = localStorage.getItem('token')
  try {
    const response = await axios.get('/api/users/check-days', {
      headers: {
        'token': token,
      }
    })
    if (response.data.code == 1) {
      checkedDates.value = response.data.data
    } else {
      ElMessage({
        message: '获取签到历史失败!',
        type: 'error',
      })
    }
  } catch (error) {
    ElMessage({
      message: '获取签到历史请求发送失败',
      type: 'error',
    })
  }
}

const checkIn = async () => {
  const token = localStorage.getItem('token')
  const currentDate = new Date().toISOString().slice(0, 10)
  console.log(currentDate)
  if (checkedDates.value.includes(currentDate)) {
    ElMessage({
      message: '签到失败，今日已经签到过!',
      type: 'warning',
    })
    return
  }
  try {
    const response = await axios.post('/api/users/check-in', {}, {
      headers: {
        'token': token,
      }
    })
    console.log(response)
    if (response.data.code == 1) {
      checkedDates.value.push(currentDate)
      ElMessage({
        message: '签到成功，积分+5!',
        type: 'success',
      })
    } else {
      ElMessage({
        message: '签到失败!',
        type: 'error',
      })
    }
  } catch (error) {
    ElMessage({
      message: '签到请求发送失败',
      type: 'error',
    })
  }
}

const isChecked = (current: Dayjs) => {
  return checkedDates.value.includes(current.format('YYYY-MM-DD'))
}
</script>

<style lang="scss">
.check-mark {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1;
}

.checkin-button-box {
  display: flex;
  justify-content: center;
}

.checkin-button {
  background-color: #f2f2fb;
}
</style>
