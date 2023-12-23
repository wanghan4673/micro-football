<script>
import axios from "axios";
export default {
    data() {
        return {
            
        }
    },
    methods: {
        async init(){
            this.getSysInfo();
        },
        async getSysInfo(){
            let response
            let token = localStorage.getItem('adminToken')
            token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIiLCJleHAiOjE3MDMxNDM1MjJ9.Quu3TIMlp7tult5i1sV0y_InxnjuV - ckyTNxL4idK3A"
            try {
                response = await axios.get("/api/admin/sysInfo/getSysInfo", {
                    headers: {
                        'token': `${token}`
                    }
                });
                if (response.status == 200) {
                    console.log(response.data)
                    
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

    </el-card>
</template>

<style scoped>

</style>