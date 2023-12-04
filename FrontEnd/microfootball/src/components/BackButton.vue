<template>
    <el-icon @click="goBack" class="back-icon">
        <Back />
    </el-icon>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()
const goBack = () => {
    router.go(-1)
}
</script>

<style lang="scss" scoped>
.back-icon {
    font-size: 4vh;
    cursor: pointer;
}
</style>