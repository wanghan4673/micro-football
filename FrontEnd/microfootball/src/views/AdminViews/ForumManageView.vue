<template>
    <el-container style="height: 80vh;margin-top: 5vh;" class="set-horizonal">
        <el-container style="margin-left: 5vw;width: 55vw;margin-top: 4vh;" class="set-vertical">
            <el-card style="height: 75vh;">
                <h3>论坛帖子列表</h3>
                <el-scrollbar height="68vh">
                    <el-card v-for="(item,index) in allPost" :key="index" style="margin-top: 2vh;" @click="getDetailId(item.id)">
                        <h3>{{ item.title }}</h3>
                        <el-text line-clamp="4" style="margin-top: 1vh;">{{ convertHtmlToText(item.content) }}</el-text>
                        <el-container>{{ item.time }}</el-container>
                    </el-card>
                </el-scrollbar>
            </el-card>
        </el-container>
        <el-container class="set-vertical" style="margin-right: 5vw;width: 30vw;margin-left: 5vw;margin-top: 4vh;">
            <el-card style="height: 35vh;">
                <h4>帖子详情</h4>
                <h3 style="margin-top: 1vh;">{{ reportedPost.title }}</h3>
                <el-container><span v-html="reportedPost.content"></span></el-container>
                <el-container>{{ reportedPost.time }}</el-container>
            </el-card>
            <el-card style="margin-top: 2vh;height: 38vh;">
                <h4>待处理举报</h4>
                <new-reported-posts @showDetailId="getDetailId" style="height: 33vh;margin-top: 2vh;"/>
            </el-card>
        </el-container>
    </el-container>
</template>

<script>
import NewPostsChart from '@/components/AdminComponents/NewPostsChart.vue'
import NewReportedPosts from '@/components/AdminComponents/NewReportedPosts.vue'
import axios from "axios"
export default {
    components: {
        NewPostsChart,
        NewReportedPosts,
    },
    data() {
        return {
            allPost:[],
            showDetailId:'',
            reportedPost:{},
        }
    },
    methods: {
        async init(){
            this.getAllPost()
        },
        async getAllPost(){
            const adminToken = localStorage.getItem('adminToken');
            try {
                const response = await axios.get('/api/admin/forum/posts', {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                if (response.status == 200) {
                    this.allPost = response.data.data;
                }
            } catch (e) {
                console.log(e)
            }
        },
        convertHtmlToText(htmlString) {
            // 创建一个新的DOMParser实例
            const parser = new DOMParser();

            // 使用DOMParser解析HTML字符串
            const parsedDocument = parser.parseFromString(htmlString, 'text/html');

            // 获取解析后文档的文本内容
            const plainText = parsedDocument.body.textContent || "";

            return plainText;
        },
        async getDetailId(value){
            this.showDetailId=value
            const adminToken = localStorage.getItem('adminToken');
            try {
                const response = await axios.get('/api/admin/forum/post/'+this.showDetailId, {
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
    mounted(){
        this.init()
    },
}
</script>

<style scoped>
.set-vertical {
    display: flex;
    flex-direction: column;
}

.set-horizonal {
    display: flex;
    flex-direction: row;
}
</style>