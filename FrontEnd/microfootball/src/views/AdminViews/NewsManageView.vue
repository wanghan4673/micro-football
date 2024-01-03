<template>
    <el-container style="height: 80vh;margin-top: 5vh;" class="set-horizonal">
        <el-card style="height: 80vh;width: 70%;margin-left: 15%;">
            <el-card v-for="(item,index) in news" :key="index" style="margin-top: 1vh;border-radius: 15px;">
                <el-container style="display: flex;flex-direction: row;">
                    <el-container style="display: flex;flex-direction: column;">
                        <h3>{{item.title}}</h3>
                        <el-container>{{ item.summary }}</el-container>
                        <h4><el-icon style="margin-top: 1vh;margin-right: 1vw;"><Clock /></el-icon>{{ item.publishTime }}</h4>
                    </el-container>
                    <el-button type="danger" @click="delNews(item.news_id,index)">
                        删除新闻
                    </el-button>
                </el-container>
            </el-card>
        </el-card>
    </el-container>
</template>

<script>
import axios from "axios";
export default {
    components: {
        
    },
    data() {
        return {
            news:[
                {
                    title:"223232",
                    summary:"sfsadasdsada",
                    id:1,
                    publishTime:"2222-22-22"
                },
                 {
                    title: "223232",
                    summary: "sfsadasdsada",
                    id: 1,
                    publishTime: "2222-22-22"
                },
                 {
                    title: "223232",
                    summary: "sfsadasdsada",
                    id: 1,
                    publishTime: "2222-22-22"
                },
                 {
                    title: "223232",
                    summary: "sfsadasdsada",
                    id: 1,
                    publishTime: "2222-22-22"
                },
            ]
        }
    },
    methods: {
        async delNews(id,index){
            try {
                const response = await axios.post('/api/admin/news/', {
                    email: this.loginForm.email,
                });
                console.log(response)
                if (response.data.code == 1) {
                    ElMessage({
                        message: '已删除新闻',
                        type: 'success',
                    })
                    this.news.splice(index,1);
                }
            } catch (e) {
                
            }
        },
        async getNews(){
            const adminToken = localStorage.getItem('adminToken');
            try {
                const response = await axios.post('/api/admin/news', {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                console.log(response)
                if (response.data.code == 1) {
                    ElMessage({
                        message: '验证码已发送，有效时间五分钟',
                        type: 'success',
                    })
                    this.isAble = false
                }
            } catch (e) {
                
            }
        },
    },
    mounted(){
        this.getNews();
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
.news-list-card{
    height: 80vh;
}
</style>