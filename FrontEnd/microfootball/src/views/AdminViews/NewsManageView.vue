<template>
    <el-container style="height: 80vh;margin-top: 5vh;" class="set-horizonal">
        <el-card style="height: 80vh;width: 70%;margin-left: 15%;">
            <el-scrollbar style="height: 78vh;">
                <el-card v-for="(item,index) in news" :key="index" style="margin-top: 1vh;border-radius: 15px;">
                    <h3>{{item.title}}</h3>
                    <el-container>{{ item.summary }}</el-container>
                    <h4><el-icon style="margin-top: 1vh;margin-right: 1vw;"><Clock /></el-icon>{{ item.publishdatetime }}</h4>
                </el-card>
            </el-scrollbar>
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
            news:[],
        }
    },
    methods: {
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
                if (response.data.code == 200) {
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
                console.log(this.news)
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