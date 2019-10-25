package ru.itmo.tpl.util;

import ru.itmo.tpl.model.Post;
import ru.itmo.tpl.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mikhail Mirzayanov", Color.BLUE),
            new User(2, "tourist", "Genady Korotkevich", Color.GREEN),
            new User(3, "emusk", "Elon Musk", Color.RED),
            new User(5, "pashka", "Pavel Mavrin", Color.GREEN),
            new User(7, "geranazavr555", "Georgiy Nazarov", Color.RED),
            new User(11, "cannon147", "Erofey Bashunov", Color.BLUE),
            new User(121289313, "Darui99", "Egor Kurbatov", Color.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Codeforces Round #595 (Div.3 )", "Привет! В вторник, 22 октября 2019 г. в 17:35 начнётся Codeforces Round #595 (Div. 3) — очередной Codeforces раунд для третьего дивизиона. В этом раунде будет 6 или 7 задач (или 8), которые подобраны по сложности так, чтобы составить интересное соревнование для участников с рейтингами до 1600. Однако все желающие, чей рейтинг 1600 и выше могут зарегистрироваться на раунд вне конкурса.\n" +
                    "\n" +
                    "Раунд пройдет по правилам образовательных раундов. Таким образом, во время раунда задачи будут тестироваться на предварительных тестах, а после раунда будет 12-ти часовая фаза открытых взломов. Я постарался сделать приличные тесты — так же как и вы буду расстроен, если у многих попадают решения после окончания контеста.\n" +
                    "\n" +
                    "Вам будет предложено 6 или 7 (или 8) задач и 2 часа на их решение.\n" +
                    "\n" +
                    "Штраф за неверную попытку в этом раунде (и последующих Div. 3 раундах) будет равняться 10 минутам.", 1),
            new Post(2, "Hello, world!!!", "Hello, world!!!", 2),
            new Post(3, "Bye, world!!!", "Bye, world!!!", 2),
            new Post(4, "The best anime", "\"Jojo's Bizarre Adventure\" is the best anime", 121289313)
    );

    private static List<Post> getPosts() {
        return POSTS;
    }

    private static List<User> getUsers() {
        return USERS;
    }

    public static void putData(Map<String, Object> data) {
        data.put("users", getUsers());
        data.put("posts", getPosts());

        String logged_user_id = String.valueOf(data.get("logged_user_id"));
        for (User user : getUsers()) {
            if (String.valueOf(user.getId()).equals(logged_user_id)) {
                data.put("user", user);
            }
        }
    }
}
