package maven.test;


/**
 * 单元测试
 * @author developer
 */
// 告诉spring怎样执行
/*@RunWith(SpringJUnit4ClassRunner.class)
//  标明是web应用测试
@WebAppConfiguration(value = "src/main/webapp") //可以不填，默认此目录
// 配置文件地址
@ContextConfiguration(locations = { "file:src/main/resources/applicationContext.xml", "file:src/main/resources/spring-mybatis.xml", "file:src/main/resources/springMVC-servlet.xml" })*/
public class UtilTest { 

   /* @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;*/

  /*  @Before
    public void before(){
        System.out.println("准备测试！！！");
    }

    @After
    public void After(){
        System.out.println("测试结束！！！");
    }*/

    /*@Test
    public void get() {
        User user = userMapper.selectByPrimaryKey("1");
        System.out.println(JSON.toJSONString(user));
    }

    @Test
    public void add(){
        User user = new User();
        user.setId("7");
        user.setName("wodemingzi");
        user.setPwd("!@#$%^&");
        userService.insert(user);
    }
*/
}