<template>
    <el-card>
        <h3>往期公告列表</h3>
        <el-scrollbar height="70vh">
            <el-card v-for="(item,index) in announcement" :key="index" class="single-card">
                <h3>{{ item.title }}</h3>
                <h4>{{ item.detail }}</h4>
                <el-container style="display: flex;align-items: center;margin-top: 1vh;float: right;">
                    <el-icon size="1em"><Clock /></el-icon>
                    <span style="margin-left: 0.5vw">{{ item.time }}</span>
                </el-container>
            </el-card>
        </el-scrollbar>
    </el-card>
</template>

<script>
import axios from "axios";
export default {
    name: 'PastAnnouncement',
    props: {
        
    },
    data() {
        return {
            announcement:[],
        }
    },
    mounted(){
        this.initPage();
    },
    methods: {
        async getAnnouncementList() {
            try {
                const response = await axios.get('/api/admin/announcement/getAnnouncementList', {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                console.log(response)
                if (response.status == 200) {
                    this.announcement=response.data.data;
                }
            } catch (e) {
                console.log(e)
            }
        },
        initPage(){
            this.getAnnouncementList();
            console.log(this.announcement);
        }
    },
}
</script>

<style scoped>
.single-card{
    width: 90%;
    margin-left: 5%;
    height: 15vh;
    margin-top: 2vh;
    border-radius: 15px;
    cursor: pointer;
}
.single-card:hover{
    transform: scale(1.1);
    transition: transform 0.2s ease-in-out;
}
</style>