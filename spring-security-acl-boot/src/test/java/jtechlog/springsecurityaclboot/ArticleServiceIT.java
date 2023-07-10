package jtechlog.springsecurityaclboot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ArticleServiceIT {

    @Autowired
    ArticleService articleService;

    @Autowired
    MutableAclService aclService;

    @BeforeEach
    // https://github.com/spring-projects/spring-security/issues/5067
    // @BeforeEach annotated method uses security context from @Test method that will be run after it
    void init() {
        asUser("admin1", "ROLE_ADMIN");
        aclService.deleteAcl(new ObjectIdentityImpl(Article.class, 1), true);
        aclService.deleteAcl(new ObjectIdentityImpl(Article.class, 2), true);

        Article article1 = new Article(1L, "test");
        articleService.createArticle(article1);

        articleService.grantPermission("user1", article1, new ArticlePermission[]{ArticlePermission.READ, ArticlePermission.WRITE});
        articleService.grantPermission("user2", article1, new ArticlePermission[]{ArticlePermission.READ});

        Article article2 = new Article(2L, "test");
        articleService.createArticle(article2);
        articleService.grantPermission("user1", article2, new ArticlePermission[]{ArticlePermission.READ, ArticlePermission.WRITE});
    }

    @Test
    void testGrantPermissionAuthenticationRequired() {
        asUser("user1", "ROLE_USER");
        assertThrows(NotFoundException.class, () -> {
            articleService.grantPermission("user1", new Article(1L, "test"), new ArticlePermission[]{ArticlePermission.READ, ArticlePermission.WRITE});
        });
    }

    @Test
    void testUserWithReadAndWrite() {
        asUser("user1", "ROLE_USER");
        articleService.findArticleById(1);
        articleService.updateArticle(1L, "test");
    }

    @Test
    public void testUserWithRead() {
        asUser("user2", "ROLE_USER");
        articleService.findArticleById(1);
        assertThrows(AccessDeniedException.class, () -> {
            articleService.updateArticle(1L, "test");
        });
    }

    @Test
    public void testUserWithNothing() {
        asUser("user3", "ROLE_USER");
        assertThrows(AccessDeniedException.class, () -> {
            articleService.findArticleById(1L);
        });
    }

    @Test
    public void testUserUpdateWithNothing() {
        asUser("user3", "ROLE_USER");
        assertThrows(AccessDeniedException.class, () -> {
            articleService.updateArticle(1L, "test");
        });
    }

    @Test
    public void testFilterAdmin() {
        asUser("admin1", "ROLE_ADMIN");
        List<Article> articles = articleService.findAllArticles();
        assertEquals(2, articles.size());
    }

    @Test
    public void testFilterUser1() {
        asUser("user1", "ROLE_USER");
        List<Article> articles = articleService.findAllArticles();
        assertEquals(2, articles.size());
    }

    @Test
    public void testFilterUser2() {
        asUser("user2", "ROLE_USER");
        List<Article> articles = articleService.findAllArticles();
        assertEquals(1, articles.size());
    }

    @Test
    public void testFilterUser3() {
        asUser("user3", "ROLE_USER");
        List<Article> articles = articleService.findAllArticles();
        assertEquals(0, articles.size());
    }

    void asUser(String username, String... authorities) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, "pass1",
                Arrays.stream(authorities).map(SimpleGrantedAuthority::new).toList()));

    }
}
