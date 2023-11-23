<template>
    <div>
        <div class="home-TUIKit-main">
            <div :class="env?.isH5 ? 'conversation-h5' : 'conversation'"
                v-show="!env?.isH5 || currentModel === 'conversation'">
                <TUISearch class="search" />
                <TUIConversation />
            </div>
            <div class="chat" v-show="!env?.isH5 || currentModel === 'message'">
                <TUIChat>
                    
                </TUIChat>
            </div>
        </div>

    </div>
</template>

<script setup>

</script>

<style lang="scss" scoped></style>