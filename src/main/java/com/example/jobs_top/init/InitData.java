package com.example.jobs_top.init;

import com.example.jobs_top.dto.req.RegisterRequest;
import com.example.jobs_top.model.*;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.JobType;
import com.example.jobs_top.repository.*;
import com.example.jobs_top.service.AuthService;
import com.example.jobs_top.service.ElasticService;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class InitData implements CommandLineRunner {

    private final AuthService authService;
    private final CandidateRepository candidateRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final VectorStore vectorStore;
    private final ChatMemory chatMemory;
    private final EmbeddingModel embeddingModel;
    private final ElasticService elasticService;


    public InitData(AuthService authService, CandidateRepository candidateRepository, AccountRepository accountRepository, CategoryRepository categoryRepository, CompanyRepository companyRepository, JobRepository jobRepository, ApplicationRepository applicationRepository, VectorStore vectorStore, ChatMemory chatMemory, EmbeddingModel embeddingModel, ElasticService elasticService) {

        this.authService = authService;
        this.candidateRepository = candidateRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.vectorStore = vectorStore;

        this.chatMemory = chatMemory;
        this.embeddingModel = embeddingModel;
        this.elasticService = elasticService;
    }

    public void initRoles(){
        String[] roleNames = { "ADMIN", "USER", "RECRUITER" };


    }

    public void initUsers(){
        String[] userNames = { "admin", "recruiter", "user"};
        Arrays.stream(userNames).forEach((username)->{
            RegisterRequest registerRequest=new RegisterRequest();
            registerRequest.setPassword("123456");
            registerRequest.setEmail(username+"@gmail.com");
            authService.register(registerRequest);
        });

    }

    public void initCategory(){
        String[] categories = { "Công nghệ thông tin", "Marketing", "Tài chính và kế toán", "Pháp lý"};
        Arrays.stream(categories).forEach((name)->{
           categoryRepository.save(new Category(name));
        });

    }

    public void initUserProfile(){
        Optional<Account> user= Optional.ofNullable(accountRepository.findByEmail("user").orElseThrow(() -> new RuntimeException("User not found")));
        Candidate candidate =new Candidate();

        candidate.setAddress("Hà Nội");
        candidate.setPhone("012345678");


        //candidate.setSkills("JAVA, C#, C++");
        candidate.setDateOfBirth(LocalDate.of(2003, 5, 21));
        candidateRepository.save(candidate);

    }



    public void initRecruiterProfile(){
        Optional<Account> user= Optional.ofNullable(accountRepository.findByEmail("recruiter").orElseThrow(() -> new RuntimeException("User not found")));
        Category category=categoryRepository.findByName("IT");
        Company recruiterProfile=new Company();
        /*recruiterProfile.setCompanyName("IT GROUP");
        recruiterProfile.setCompanyAddress("Số 9 ngõ 4 Duy Tân, Cầu Giấy, Hà Nội, Cầu Giấy");
        recruiterProfile.setCategory(category);
        recruiterProfile.setCompanyLogo("link image");
        recruiterProfile.setNation("Việt Nam");
        recruiterProfile.setDescription("Thành lập từ năm 2014, với 7 thành viên. Cho đến nay, DTS lớn dần, phát triển lên số lượng 70 thành viên.\n" +
                "\n" +
                "Chậm mà chắc, là tiêu chí mà DTS lựa chọn trên con đường phát triển của mình. Chúng tôi chú trọng đào tạo và phát triển đội ngũ, level tăng liên tục sau mỗi dự án.\n" +
                "\n" +
                "Ở DTS, mỗi CBNV có 1 lộ trình phát triển khác nhau, phù hợp với mong muốn từng người, và thực tế của Công ty.\n" +
                "\n" +
                " Chúng tôi chuyên sâu phát triển về các ứng dụng, dự án liên quan đến giáo dục, y tế. Năm 2019, thành lập studio game, với tham vọng chinh phục khách hàng Âu - Mỹ.\n" +
                "\n" +
                "Ở DTS, hàng năm, mỗi CBNV được reivew 2-3 lần/năm. Năng lực được nhìn nhận và đãi ngộ xứng đáng. ");
        recruiterProfile.setCompanySize("25-99 nhân viên");
        recruiterProfile.setCompanyWebsite("itgroup.com.vn");
        recruiterProfile.setUser(user.get());*/
        companyRepository.save(recruiterProfile);
    }

    public static JobType getRandomJobType() {
        JobType[] jobTypes = JobType.values();
        int randomIndex = new Random().nextInt(jobTypes.length);
        return jobTypes[randomIndex];
    }

    public static ExperienceLevel getRandomJobLevel() {
        ExperienceLevel[] experienceLevels = ExperienceLevel.values();
        int randomIndex = new Random().nextInt(experienceLevels.length);
        return experienceLevels[randomIndex];
    }

    public static LocalDate randomApplicationDeadline() {
        LocalDate now = LocalDate.now();
        int randomDays = ThreadLocalRandom.current().nextInt(30, 91);
        return now.plusDays(randomDays);
    }

    public static <K> K getRandomKey(Map<K, ?> map) {
        List<K> keys = new ArrayList<>(map.keySet());
        Random random = new Random();
        return keys.get(random.nextInt(keys.size()));
    }

    public List<String> generateRequirementsBasedOnLevel(String language, ExperienceLevel level, Map<String, List<String>> requirementsMap) {
        List<String> baseRequirements = new ArrayList<>(requirementsMap.get(language));
        baseRequirements.add(level.getLabel());
        return baseRequirements;



    }

    public static String getRandomTitle(ExperienceLevel experienceLevel) {
        return switch (experienceLevel) {
            case NO_REQUIREMENT, LESS_THAN_ONE_YEAR -> "Thực tập sinh lập trình";
            case ONE_YEAR, TWO_YEARS -> "Lập trình viên Fresher";
            case THREE_YEARS -> "Lập trình viên Junior";
            case FOUR_YEARS -> "Lập trình viên Mid-level";
            case FIVE_YEARS -> "Lập trình viên Senior";
            case MORE_THAN_FIVE_YEARS -> "Tech Lead / Senior Developer";
        };
    }

    public static int[] getRandomSalaryRange(ExperienceLevel experienceLevel) {
        return switch (experienceLevel) {
            case NO_REQUIREMENT, LESS_THAN_ONE_YEAR -> new int[]{6, 10};
            case ONE_YEAR, TWO_YEARS -> new int[]{10, 15};
            case THREE_YEARS -> new int[]{15, 20};
            case FOUR_YEARS -> new int[]{20, 30};
            case FIVE_YEARS -> new int[]{30, 40};
            case MORE_THAN_FIVE_YEARS -> new int[]{40, 60};
        };
    }


    public void initJobs(int quantity, Company recruiterProfile){
        Map<String, List<String>> requirementsMap = new HashMap<>();

        List<String> techBenefits = Arrays.asList(
                "Mức lương cạnh tranh và thưởng theo hiệu suất",
                "Chế độ làm việc hybrid (văn phòng và từ xa)",
                "Được cấp laptop hoặc thiết bị làm việc",
                "Bảo hiểm sức khỏe cao cấp",
                "Khóa học nâng cao kỹ năng miễn phí",
                "Du lịch hàng năm và team building",
                "Cơ hội làm việc với công nghệ mới nhất",
                "Được tài trợ chi phí thi các chứng chỉ quốc tế"
        );

        // Thêm yêu cầu cho Java
        requirementsMap.put("Java", Arrays.asList(
                "Thành thạo Java Core",
                "Có kinh nghiệm với Spring Framework",
                "Hiểu biết về Hibernate/JPA",
                "Sử dụng thành thạo Maven hoặc Gradle",
                "Có kinh nghiệm viết Unit Test"
        ));

        // Thêm yêu cầu cho C#
        requirementsMap.put("C#", Arrays.asList(
                "Thành thạo ngôn ngữ lập trình C#",
                "Kinh nghiệm làm việc với .NET hoặc .NET Core",
                "Hiểu biết về LINQ và Entity Framework",
                "Khả năng làm việc với Visual Studio",
                "Kinh nghiệm thiết kế RESTful API"
        ));

        // Thêm yêu cầu cho C++
        requirementsMap.put("C++", Arrays.asList(
                "Thành thạo C++ (C++11 hoặc mới hơn)",
                "Kinh nghiệm làm việc với STL",
                "Hiểu biết về quản lý bộ nhớ và con trỏ",
                "Kinh nghiệm làm việc với các hệ thống nhúng hoặc thời gian thực",
                "Hiểu biết về lập trình đa luồng"
        ));

        List<String> locations = Arrays.asList(
                "Hà Nội", "TP. Hồ Chí Minh", "Đà Nẵng", "Cần Thơ", "Bình Dương", "Hải Phòng", "Quảng Ninh"
        );

        List<String> workSchedules = Arrays.asList(
                "Thứ 2 - Thứ 6 (từ 08:00 đến 17:00)",
                "Thứ 2 - Thứ 7 (từ 09:00 đến 18:00)",
                "Thứ 2 - Thứ 6 (từ 10:00 đến 19:00)",
                "Thứ 2 - Thứ 6 (từ 08:30 đến 17:30)",
                "Từ Thứ 2 đến Thứ 5 (từ 08:00 đến 17:00)"
        );

        Random random = new Random();
        for(int i=0;i<quantity;i++){
            Job job=new Job();

            ExperienceLevel experienceLevel = getRandomJobLevel();
            job.setExperienceLevel(experienceLevel);
            job.setDescription("Lập trình web và ứng dụng mobile.");
            String randomLanguage = getRandomKey(requirementsMap);
            String jobTitle = getRandomTitle(experienceLevel);
            job.setTitle(jobTitle + " - " + randomLanguage);
            List<String> requirements = generateRequirementsBasedOnLevel(randomLanguage, experienceLevel, requirementsMap);
            job.setRequirements(String.join("\n", requirements));
            job.setBenefits(String.join("\n", techBenefits.subList(0, random.nextInt(techBenefits.size()) + 1)));
            job.setJobType(getRandomJobType());
            String location = locations.get(random.nextInt(locations.size()));
            job.setLocation(location);
            job.setApplicationDeadline(randomApplicationDeadline());
            int[] salaryRange = getRandomSalaryRange(experienceLevel);
            int salaryMin = salaryRange[0];
            int salaryMax = salaryRange[1];
            job.setSalaryMin(salaryMin);
            job.setSalaryMax(salaryMax);
            String workSchedule = workSchedules.get(random.nextInt(workSchedules.size()));
            job.setWorkSchedule(workSchedule);
            jobRepository.save(job);

        }

    }

    public void initApplication(){
        /*UserProfile userProfile=userProfileRepository.findByFullName("Nguyễn Văn A");
        Optional<Job> job=jobRepository.findById(1L);
        Application application=new Application();

        application.setJob(job.get());
        //application.setStatus(ApplicationStatus.APPLIED);
        applicationRepository.save(application);*/

    }

    public void testVectorStore(){
        TokenTextSplitter tokenTextSplitter=new TokenTextSplitter();
        List<Document> documents=new ArrayList<Document>();
        List<Job> jobs=jobRepository.findAll();
        for(Job job:jobs){
            Document document=new Document(
                    job.getTitle()+" "+job.getDescription(),
                    Map.of("jobType", job.getJobType().toString(),
                            "location", job.getLocation(),
                            "applicationDeadline",job.getApplicationDeadline().toString(),
                            "title",job.getTitle(),
                            "description",job.getDescription(),
                            "level",job.getExperienceLevel().toString(),
                            "salaryMax",job.getSalaryMax(),
                            "workSchedule",job.getWorkSchedule(),
                            "salaryMin",job.getSalaryMin())
            );


            var splitter=tokenTextSplitter.apply(List.of(document));
            vectorStore.add(splitter);

        }
        System.out.println("ok");
    }

    public void testVectorStore1(){
        List<Document> documents=new ArrayList<Document>();
        List<Job> jobs=jobRepository.findAll();
        for(Job job:jobs){
            String content = String.format(
                    "Title: %s. Description: %s. Type: %s. Location: %s. Level: %s. Salary: %s - %s. Work Schedule: %s. Deadline: %s.",
                    job.getTitle(),
                    job.getDescription(),
                    job.getJobType().toString(),
                    job.getLocation(),
                    job.getExperienceLevel().toString(),
                    job.getSalaryMin(),
                    job.getSalaryMax(),
                    job.getWorkSchedule(),
                    job.getApplicationDeadline()
            );

            // Chuyển đổi nội dung thành vector embedding bằng AI
            float[] embedding = embeddingModel.embed(content);

            // Lưu vào metadata để dễ truy xuất
            Map<String, Object> metadata = Map.of(
                    "jobId", job.getId(),
                    "jobType", job.getJobType().toString(),
                    "location", job.getLocation(),
                    "level", job.getExperienceLevel().toString(),
                    "salaryMin", job.getSalaryMin(),
                    "salaryMax", job.getSalaryMax(),
                    "workSchedule", job.getWorkSchedule(),
                    "applicationDeadline", job.getApplicationDeadline().toString()
            );

            // Tạo Document từ embedding và metadata
            Document document = new Document(content, metadata);
            //document.setEmbedding(embedding);
            documents.add(document);


        }
        vectorStore.add(documents);
        System.out.println("ok");
    }

    @Override
    public void run(String... args) throws Exception {
        //List<Job> jobs=jobRepository.findAll();
        //jobs.forEach(elasticService::saveJobDocument);
        //elasticService.deleteAllDocuments("custom_index3");
        //initRoles();
        //initUsers();
        //initCategory();
        //initRecruiterProfile();
        //RecruiterProfile recruiterProfile= recruiterProfileRepository.findByName("IT GROUP");
        //initJobs(10,recruiterProfile);
        //initApplication();
        //testVectorStore1();
        /*String query="C# senior";

        List<Document> results= vectorStore.similaritySearch(SearchRequest.query(query).withTopK(100).withSimilarityThreshold(0.75));
        System.out.println("🔍 Search results for: " + query);
        if (results.isEmpty()) {
            System.out.println("❌ No results found.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                Document doc = results.get(i);
                System.out.println("✅ Result " + (i + 1) + ":");
                System.out.println("   📄 ID: " + doc.getId());
                System.out.println("   📜 Content: " + doc.getContent());
                System.out.println("   🏷 Metadata: " + doc.getMetadata());
                System.out.println("-----------------------------");
            }
        }*/



        //System.out.println(applicationRepository.findLatestApplicationByCriteria("IT GROUP","Java Developer","jojo"));





    }
}
