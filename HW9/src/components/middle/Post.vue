<template>
    <article>
        <div class="title"><a href="#page=FullPost" @click="changePage('FullPost', post.id)">{{post.title}}</a></div>
        <div class="information">By {{users[post.userId].login}}</div>
        <div class="body">
            {{post.text}}
        </div>
        <ul class="attachment">
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
        </ul>
        <div class="footer">
            <div class="left">
                <img src="../../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="../../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="../../assets/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                <img src="../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">{{this.commentCount}}</a>
            </div>
        </div>
    </article>
</template>

<script>
    export default {
        name: "Post",
        props: ['post', 'users', 'comments'],
        methods: {
            changePage: function (page, id) {
                this.$root.$emit("onChangePage", page, id);
            }
        },
        computed: {
            commentCount: function () {
                return Object.values(this.comments).filter(comment => comment.postId === this.post.id).length;
            }
        }
    }
</script>

<style scoped>

</style>