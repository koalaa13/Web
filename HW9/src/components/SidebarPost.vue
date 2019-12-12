<template>
    <section>
        <div class="header">
            By {{users[post.userId].login}}
        </div>
        <div class="body">
            {{post.text}}
        </div>
        <div class="footer">
            <a href="#page=FullPost" @click="changePage('FullPost', post.id)">View all</a>
        </div>
    </section>
</template>

<script>
    export default {
        props: ['users', 'post'],
        name: "SidebarPost",
        methods: {
            changePage: function (page, postId) {
                this.$root.$emit("onChangePage", page, postId);
            }
        }
    }
</script>

<style scoped>

</style>
