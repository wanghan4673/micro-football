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
                <el-card @click="showDetail(item.postId)">
                    <p>举报理由：{{ item.reason }}</p>
                    <p>举报人：{{ item.reporterName }}</p>
                    <el-container style="margin-top: 1vh;">
                        <el-button type="primary" size="small" @click="delReport(item.id,index)">取消举报</el-button>
                        <el-button type="primary" size="small" @click="confirm(item.id, index)">删除帖子</el-button>
                    </el-container>
                </el-card>
            </el-timeline-item>
        </el-timeline>
    </el-scrollbar>
</template>

<script>
import axios from "axios"
import { ElMessage } from 'element-plus';
export default {
    data(){
        return{
            reports: [],
        }
    },
    methods:{
        async getReport(){
            const adminToken = localStorage.getItem('adminToken');
            try {
                const response = await axios.get('/api/admin/forum/getReportList', {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                if (response.status == 200) {
                    this.reports = response.data.data;
                    console.log(this.reports)
                }
            } catch (e) {
                console.log(e)
            }
        },
        async init(){
            this.getReport()
        },
        showDetail(id){
            const showDetailId = id
            this.$emit('showDetailId', showDetailId);
        },
        async delReport(id,index){
            const adminToken = localStorage.getItem('adminToken');
            const delId = id;
            const formData = new FormData()
            formData.append('id', delId);
            try {
                const response = await axios.post('/api/admin/forum/deleteReport',formData,{
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                if (response.status == 200) {
                    ElMessage.success("删除举报成功")
                    this.reports.splice(index,1)
                }
            } catch (e) {
                console.log(e)
            }
        },
        async confirm(id, index) {
            const adminToken = localStorage.getItem('adminToken');
            const delId = id;
            const formData = new FormData()
            formData.append('id', delId);
            try {
                const response = await axios.post('/api/admin/forum/confirmReport', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                if (response.status == 200) {
                    ElMessage.success("删除帖子成功")
                    setTimeout(() => {
                        window.location.reload(); // 刷新当前页面
                    }, 1000);
                }
            } catch (e) {
                console.log(e)
            }
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