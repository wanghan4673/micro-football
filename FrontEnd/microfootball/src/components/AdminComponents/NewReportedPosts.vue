<template>
    <el-scrollbar >
        <el-timeline>
            <el-timeline-item 
                v-for="(item, index) in reports"
                :key="index"
                :timestamp="item.time" 
                timestamp-color="rgb(209, 218, 225)"
                size="large"
                color="rgb(209, 218, 225)"
                placement="top"
                class="report-item-animation"
            >
                <el-card
                @mouseenter="showHoverContent(item.reportId)"
                @mouseleave="hideHoverContent()"
                >
                <!-- <h4>{{ item.reportName }}</h4> -->
                <p>{{ item.reason }}</p>
                </el-card>
            </el-timeline-item>
        </el-timeline>
    </el-scrollbar>
    <!-- <el-card v-if="hoverContent.show" class="timeline-detail">
        <h1>{{ timeTree[hoverContent.id - 1].eventTitle }}</h1>
        <h3>{{ timeTree[hoverContent.id - 1].eventContains }}</h3>
        <h3>{{ timeTree[hoverContent.id - 1].eventTime }}</h3>
    </el-card> -->
</template>

<script>
import axios from "axios"
export default {
    data(){
        return{
            reports: [],
            hoverContent: {
                show: false,
                id: -1,
            },
        }
    },
    methods:{
        showHoverContent(id) {
            this.hoverContent.show = true;
            this.hoverContent.id = id;
        },
        hideHoverContent() {
            this.hoverContent.show = false;
            this.hoverContent.id = -1;
        },
        async getReport(){
            const adminToken = localStorage.getItem('adminToken');
            try {
                const response = await axios.get('/api/admin/forum/getReportList', {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                console.log(response)
                if (response.status == 200) {
                    this.reports = response.data.data;
                }
            } catch (e) {
                console.log(e)
            }
        },
        async init(){
            this.getReport()
        }
    },
    mounted(){
        this.init()
    }
}
</script>

<style scoped>
.report-item-animation {
    margin-left: 0;
  opacity: 0; /* 初始时设置不可见 */
  transform: translateY(20px); /* 初始位置在下方 */

  /* 使用过渡实现渐变显示和位置移动动画 */
  transition: opacity 5s ease, transform 5s ease;

  /* 使用 @keyframes 定义动画 */
  animation: reportItemShow 3s ease forwards;
}

@keyframes reportItemShow {
  from {
    opacity: 0; /* 初始不可见 */
    transform: translateY(20px); /* 初始位置在下方 */
  }
  to {
    opacity: 1; /* 最终可见 */
    transform: translateY(0); /* 最终位置在原始位置 */
  }
}

</style>