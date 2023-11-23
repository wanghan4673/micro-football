<template>
    <div>
        <div class="home-TUIKit-main">
            <div :class="env?.isH5 ? 'conversation-h5' : 'conversation'"
                v-show="!env?.isH5 || currentModel === 'conversation'">
                <TUISearch class="search" />
                <TUIConversation @current="handleCurrentConversation" />
            </div>
            <div class="chat" v-show="!env?.isH5 || currentModel === 'message'">
                <TUIChat>
                    <h1>欢迎使用腾讯云即时通信IM</h1>
                </TUIChat>
            </div>
            <!-- TUICallKit 组件：通话 UI 组件主体 -->
            <TUICallKit :class="!showCallMini ? 'callkit-drag-container' : 'callkit-drag-container-mini'"
                :allowedMinimized="true" :allowedFullScreen="false" :beforeCalling="beforeCalling"
                :afterCalling="afterCalling" :onMinimized="onMinimized" :onMessageSentByMe="onMessageSentByMe" />
        </div>

    </div>
</template>

<script setup>

</script>

<style lang="scss" scoped></style>