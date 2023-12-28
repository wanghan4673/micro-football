<template>
    <el-container style="height: 80vh;margin-top: 5vh;" class="set-horizonal">
        <el-container style="margin-left: 5vw;width: 55vw;" class="set-vertical">
            <el-switch
                v-model="viewUserBanned"
                style="--el-switch-on-color: #ff4949; --el-switch-off-color: #13ce66"
                active-text="被封禁帖子列表"
                inactive-text="论坛帖子列表"
            />
            <el-card v-if="viewUserBanned == true" style="height: 75vh;">
                <el-input v-model="keyword" placeholder="根据关键词搜索帖子" clearable style="width: 20vw;">
                    <template #prefix>
                    <el-icon class="el-input__icon"><search /></el-icon>
                    </template>
                </el-input>
                <el-button type="primary" style="margin-left: 2vw;">搜索</el-button>
                <h3 style="margin-top: 2vh;">被封禁帖子列表</h3>
            </el-card>
            <el-card v-if="viewUserBanned == false" style="height: 75vh;">
                <el-input v-model="keyword" placeholder="根据关键词搜索帖子" clearable style="width: 20vw;">
                    <template #prefix>
                    <el-icon class="el-input__icon"><search /></el-icon>
                    </template>
                </el-input>
                <el-button type="primary" style="margin-left: 2vw;">搜索</el-button>
                <h3 style="margin-top: 2vh;">论坛帖子列表</h3>
                <el-table :data="allPost" style="width: 100%">
                    <el-table-column fixed prop="title" label="标题" width="150" />
                    <el-table-column prop="content" label="内容" width="120" />
                    <el-table-column prop="state" label="State" width="120" />
                    <el-table-column prop="city" label="City" width="120" />
                    <el-table-column prop="address" label="Address" width="600" />
                    <el-table-column prop="zip" label="Zip" width="120" />
                    <el-table-column fixed="right" label="Operations" width="120">
                    <template #default>
                        <el-button link type="primary" size="small" @click="handleClick"
                        >Detail</el-button
                        >
                        <el-button link type="primary" size="small">Edit</el-button>
                    </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </el-container>
        <el-container class="set-vertical" style="margin-right: 5vw;width: 30vw;margin-left: 5vw;margin-top: 4vh;">
            <new-posts-chart style="height: 35vh;"/>
            <el-card style="margin-top: 2vh;height: 38vh;">
                <h4>待处理举报</h4>
                <new-reported-posts style="height: 33vh;margin-top: 2vh;"/>
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
            viewUserBanned: false,
            allPost:[],
        }
    },
    methods: {
        async init(){
            this.getAllPost()
        },
        async getAllPost(){
            console.log(111222)
            const adminToken = localStorage.getItem('adminToken');
            try {
                const response = await axios.get('/api/admin/forum/getAllPost', {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                console.log(response)
                if (response.status == 200) {
                    this.allPost = response.data.data;
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