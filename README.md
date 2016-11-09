How to use @Inject thingy

Your class has to extend BaseActivity
Use @Inject on the repository variable
Call getComponent().inject(this);

```java
	@Inject
    Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getComponent().inject(this);
    }
```