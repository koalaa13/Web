<template>
    <div class="comments">
        <Post :post="post" :users="users"/>
        <div class="comment" v-for="comment in getComments(post)" :key="comment.id">
            {{comment.text}}
            <div class="information">
                By {{users[comment.userId].login}}
            </div>
        </div>
    </div>
</template>

<script>
    import Post from "./Post";

    export default {
        name: "FullPost",
        props: ['post', 'users', 'comments'],
        components: {Post},
        methods: {
            getComments: function (post) {
                return Object.values(this.comments).filter(comment => comment.postId === post.id);
            }
        }
    }
</script>

<style scoped>

</style>