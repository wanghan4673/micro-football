<script>
import axios from "axios";
export default {
    data() {
        return {
            sysData:{},
        }
    },
    methods: {
        async init(){
            this.getSysInfo();
        },
        async getSysInfo(){
            let response
            const adminToken = localStorage.getItem('adminToken');
            try {
                response = await axios.get("/api/admin/sysInfo/getSysInfo", {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                console.log(response)
                if (response.status == 200) {
                    this.sysData=response.data.data
                    
                }
            } catch (error) {
                console.log(error)
            }
        }
    },
    mounted(){
        this.init();
    }
}
</script>

<template>
    <el-card>
        <h3>
            昨日系统信息
        </h3>
        <el-container class="sub-box" style="background-color: rgb(246, 148, 148);">
            <span class="sub-text">昨日新增用户：{{ sysData.newUserNum }}</span>
        </el-container>
        <el-container class="sub-box" style="background-color: rgb(156, 233, 49);">
            <span class="sub-text">昨日新增帖子：{{ sysData.newPostNum }}</span>
        </el-container>
        <el-container class="sub-box" style="background-color: rgb(148, 174, 246);">
            <span class="sub-text">昨日新增新闻：{{ sysData.newNewsNum }}</span>
        </el-container>
        <el-container class="sub-box" style="background-color: rgb(217, 246, 148);">
            <span class="sub-text">昨日新增公告：{{ sysData.newAnnounceNum }}</span>
        </el-container>
    </el-card>
</template>

<style scoped>
.sub-box {
  position: relative;
  border-radius: 10px;
  height: 7vh;
  width: 100%;
  margin-top:15px;
}

.sub-text {
  margin-top: 2vh;
  margin-left: 5vw;
  height: 1.5vh;
  color: rgb(54, 46, 46);
  font-weight: bold;
}
</style>