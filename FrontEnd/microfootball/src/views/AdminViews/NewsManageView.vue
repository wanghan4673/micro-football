<template>
    <el-container style="height: 80vh;margin-top: 5vh;" class="set-horizonal">
        <el-card style="height: 80vh;width: 70%;margin-left: 15%;margin-top: 4vh;">
            <el-scrollbar style="height: 78vh;">
                <el-card v-for="(item,index) in news" :key="index" style="margin-top: 1vh;border-radius: 15px;">
                    <h3>{{item.title}}</h3>
                    <div v-html="item.content"></div>
                    <h4><el-icon style="margin-top: 1vh;margin-right: 1vw;"><Clock /></el-icon>{{ item.createTime }}</h4>
                    <el-button type="primary" size="small" style="margin-top: 1vh;" @click="delNews(item.id,index)">删除新闻</el-button>
                </el-card>
            </el-scrollbar>
        </el-card>
        <el-card class="set-vertical" style="margin-right: 5vw;width: 30vw;margin-left: 5vw;margin-top: 4vh;">
            <h4>待处理举报</h4>
            <reported-news-vue style="height: 60vh;"></reported-news-vue>
        </el-card>
    </el-container>
</template>

<script>
import axios from "axios";
import { ElMessage } from 'element-plus';
import ReportedNewsVue from '@/components/AdminComponents/ReportedNews.vue';
export default {
    components: {
        ReportedNewsVue,
    },
    data() {
        return {
            news:[],
        }
    },
    methods: {
        changeTime(input){
            const inputDate = new Date(input);

            const year = inputDate.getFullYear();
            const month = (inputDate.getMonth() + 1).toString().padStart(2, '0');
            const day = inputDate.getDate().toString().padStart(2, '0');
            const hours = inputDate.getHours().toString().padStart(2, '0');
            const minutes = inputDate.getMinutes().toString().padStart(2, '0');
            const seconds = inputDate.getSeconds().toString().padStart(2, '0');

            return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        },
        async delNews(id,index){
            const adminToken = localStorage.getItem('adminToken');
            const delId = id
            try {
                const response = await axios.delete('/api/admin/news/delete/'+delId , {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
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
                const response = await axios.get('/api/admin/news', {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                this.news=response.data.data
                for (let i = 0; i < this.news.length; i++) {
                    this.news[i].content=this.news[i].content.slice(0,60)
                    this.news[i].createTime = this.changeTime(this.news[i].createTime)
                }
            } catch (e) {
                
            }
        },
    },
    created(){
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