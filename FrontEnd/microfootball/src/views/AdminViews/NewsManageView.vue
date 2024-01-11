<template>
    <el-container style="height: 80vh;margin-top: 5vh;" class="set-horizonal">
        <el-card style="height: 80vh;width: 70%;margin-left: 15%;margin-top: 4vh;">
            <el-scrollbar style="height: 78vh;">
                <el-card v-for="(item,index) in news" :key="index" style="margin-top: 1vh;border-radius: 15px;">
                    <h3>{{item.title}}</h3>
                    <el-container>{{ item.content }}</el-container>
                    <h4><el-icon style="margin-top: 1vh;margin-right: 1vw;"><Clock /></el-icon>{{ item.createTime }}</h4>
                    <el-button type="primary" size="small" style="margin-top: 1vh;" @click="delNews(item.id,index)">删除新闻</el-button>
                </el-card>
            </el-scrollbar>
        </el-card>
        <el-container class="set-vertical" style="margin-right: 5vw;width: 30vw;margin-left: 5vw;margin-top: 4vh;">
            <el-card style="height: 35vh;">
                <!-- <el-scrollbar style="height: 30vh;">
                    <h4>帖子详情</h4>
                    <h3 style="margin-top: 1vh;">{{ reportedPost.title }}</h3>
                    <el-container><span v-html="reportedPost.content"></span></el-container>
                    <el-container>{{ reportedPost.time }}</el-container>
                </el-scrollbar> -->
            </el-card>
            <el-card style="margin-top: 2vh;height: 38vh;">
                <h4>待处理举报</h4>
                <reported-news-vue @showDetailId="getDetailId" style="height: 33vh;margin-top: 2vh;"></reported-news-vue>
            </el-card>
        </el-container>
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
            showDetailId: '',
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
                const response = await axios.get('/api/admin/news', {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                this.news=response.data.data
                for (let i = 0; i < this.news.length; i++) {
                    this.news[i].createTime = this.changeTime(this.news[i].createTime)
                }
                console.log(this.news)
            } catch (e) {
                
            }
        },
        async getDetailId(value) {
            this.showDetailId = value
            const adminToken = localStorage.getItem('adminToken');
            try {
                const response = await axios.get('/api/admin/forum/post/' + this.showDetailId, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                console.log(response)
                if (response.status == 200) {
                    this.reportedPost = response.data.data;
                }
            } catch (e) {
                console.log(e)
            }
        }
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