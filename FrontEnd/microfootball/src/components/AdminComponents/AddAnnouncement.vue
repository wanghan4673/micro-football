<template>
    <el-card>
        <el-container style="margin-left: 2.5vw;">
            <h3>公告标题</h3>
            <el-input v-model="newTitle" style="width: 50%;margin-left: 2vw;" placeholder="公告标题"/>
        </el-container>
        <el-input
            v-model="newDetail"
            :rows="inputRows"
            type="textarea"
            placeholder="请输入公告"
            style="margin-top: 2vh;"
        />
        <el-container class="set-horizonal" style="margin-top: 2vh;margin-left: 10%;">
            <el-button type="primary" round style="width: 40%;" @click="postAnnouncement">发布</el-button>
            <el-button type="primary" round style="width: 40%; margin-left: 3vw;" @click="clearAll">清空</el-button>
        </el-container>
    </el-card>
</template>

<script>
import axios from "axios";
import { ElMessage } from 'element-plus'
export default {
    name:'AddAnnouncement',
    props:{
        inputRows:String,
    },
    data() {
        return {
            newTitle: "",
            newDetail: "",
        }
    },
    methods: {
        async postAnnouncement() {
            const adminToken = localStorage.getItem('adminToken');
            const formData = new FormData()
            formData.append('title', this.newTitle);
            formData.append('detail',this.newDetail);
            formData.append('receiver', 0);
            try {
                const response = await axios.post('/api/admin/announcement/announcement', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token':adminToken
                    },
                });
                console.log(response)
                if (response.status == 200) {
                    ElMessage.success("成功发布公告")
                    setTimeout(() => {
                        window.location.reload(); // 刷新当前页面
                    }, 1000);
                }
            } catch (e) {
                console.log(e)
            }
        },
        clearAll(){
            this.newDetail='';
            this.newTitle='';
        }
    },
}
</script>

<style scoped>
.set-vertical{
    display: flex;
    flex-direction: column;
}
.set-horizonal{
    display: flex;
    flex-direction: row;
}
</style>