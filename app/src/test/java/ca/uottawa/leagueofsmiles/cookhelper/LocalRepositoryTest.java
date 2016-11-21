package ca.uottawa.leagueofsmiles.cookhelper;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import ca.uottawa.leagueofsmiles.cookhelper.data.LocalRepository;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LocalRepositoryTest {

    Repository repository;

    @Before
    public void init() {
        repository = new LocalRepository();
    }

    @Test
    public void insert_isCorrect() throws Exception {
        TestCase.fail();
    }

    @Test
    public void update_isCorrect() throws Exception {
        TestCase.fail();
    }

    @Test
    public void delete_isCorrect() throws Exception {
        TestCase.fail();
    }

    @Test
    public void getRecipeList_isCorrect() throws Exception {
        TestCase.fail();
    }

    @Test
    public void getRecipeById_isCorrect() throws Exception {
        TestCase.fail();
    }
}