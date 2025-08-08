# Spring Boot 命令行應用程式示範 ⚡

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 專案介紹

本專案是一個 Spring Boot 命令行應用程式的完整示範，展示了如何開發非 Web 類型的 Spring Boot 應用程式。透過實際的程式碼範例，說明 Spring Boot 在命令行環境下的各種功能和最佳實踐。

### 🎯 專案特色

- **非 Web 應用程式設計** - 展示如何關閉 Web 功能，專注於命令行操作
- **Runner 組件生命週期** - 演示 `CommandLineRunner` 和 `ApplicationRunner` 的使用方式
- **執行順序控制** - 透過 `@Order` 註解控制組件執行順序
- **退出碼管理** - 實作 `ExitCodeGenerator` 來設定應用程式退出碼
- **應用程式上下文管理** - 展示如何取得和操作 Spring 應用程式上下文

> 💡 **為什麼選擇此專案？**
> - 完整展示 Spring Boot 命令行應用程式的開發模式
> - 提供實際可執行的程式碼範例
> - 說明非 Web 應用程式的配置方式
> - 演示 Spring Boot 生命週期管理

## 技術棧

### 核心框架
- **Spring Boot 3.4.5** - 現代化的 Java 應用程式框架
- **Spring Framework** - 提供 IoC 容器和 AOP 支援
- **Maven** - 專案建置和依賴管理工具

### 開發工具與輔助
- **Lombok** - 減少樣板程式碼，提升開發效率
- **SLF4J + Logback** - 日誌記錄框架
- **JUnit 5** - 單元測試框架

## 專案結構

```
command-line-demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── tw/fengqing/spring/hello/
│   │   │       ├── CommandLineApplication.java      # 主程式入口
│   │   │       ├── FooCommandLineRunner.java       # CommandLineRunner 示範
│   │   │       ├── BarApplicationRunner.java       # ApplicationRunner 示範
│   │   │       ├── ExitApplicationRunner.java      # 應用程式退出處理
│   │   │       └── MyExitCodeGenerator.java        # 退出碼生成器
│   │   └── resources/
│   │       └── application.properties              # 應用程式配置
│   └── test/
│       └── java/
│           └── tw/fengqing/spring/hello/
│               └── CommandLineApplicationTests.java # 測試類別
├── pom.xml                                        # Maven 配置
└── README.md                                      # 專案說明文件
```

## 快速開始

### 前置需求
- **Java 21** 或更高版本
- **Maven 3.9+** 或更高版本
- **IDE** (建議使用 IntelliJ IDEA 或 Eclipse)

### 安裝與執行

1. **克隆此倉庫：**
```bash
git clone <repository-url>
cd command-line-demo
```

2. **編譯專案：**
```bash
mvn clean compile
```

3. **執行應用程式：**
```bash
# 先打包再執行
mvn package -DskipTests
java -jar target/command-line-0.0.1-SNAPSHOT.jar

# 或使用 Maven 執行（錯誤為mvn拋出異常，可忽略）
mvn spring-boot:run

```

### 預期執行結果

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.4.5)

2025-08-08T09:25:36.943+08:00  INFO 55567 --- [           main] t.f.spring.hello.CommandLineApplication  : Started CommandLineApplication in 0.417 seconds
2025-08-08T09:25:36.945+08:00  INFO 55567 --- [           main] t.f.spring.hello.FooCommandLineRunner    : Foo
2025-08-08T09:25:36.945+08:00  INFO 55567 --- [           main] t.f.spring.hello.BarApplicationRunner    : Bar
2025-08-08T09:25:36.946+08:00  INFO 55567 --- [           main] t.f.spring.hello.ExitApplicationRunner   : Exit with 1.
```

## 核心組件說明

### 1. 主程式入口 (`CommandLineApplication.java`)

```java
@SpringBootApplication
public class CommandLineApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CommandLineApplication.class)
                .web(WebApplicationType.NONE)  // 關閉 Web 功能
                .run(args);
    }
}
```

**重要說明：**
- 使用 `WebApplicationType.NONE` 關閉 Web 功能
- 適合純命令行應用程式開發

### 2. CommandLineRunner 示範 (`FooCommandLineRunner.java`)

```java
@Component
// 設置執行順序，數字越小，執行越早
@Order(1)
@Slf4j
public class FooCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("Foo");
    }
}
```

**功能說明：**
- 實作 `CommandLineRunner` 介面
- 接收原始命令列參數
- 使用 `@Order(1)` 設定最高優先級

### 3. ApplicationRunner 示範 (`BarApplicationRunner.java`)

```java
@Component
// 設置執行順序，數字越小，執行越早
@Order(2)
@Slf4j
public class BarApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Bar");
    }
}
```

**功能說明：**
- 實作 `ApplicationRunner` 介面
- 接收解析後的 `ApplicationArguments`
- 使用 `@Order(2)` 設定第二優先級

### 4. 退出碼生成器 (`MyExitCodeGenerator.java`)

```java
@Component
public class MyExitCodeGenerator implements ExitCodeGenerator {
    @Override
    public int getExitCode() {
        return 1;  // 設定退出碼為 1
    }
}
```

**功能說明：**
- 實作 `ExitCodeGenerator` 介面
- 設定應用程式的退出碼
- 非零退出碼表示異常結束

### 5. 應用程式退出處理 (`ExitApplicationRunner.java`)

```java
@Component
// 設置執行順序，數字越小，執行越早
@Order(3)
@Slf4j
public class ExitApplicationRunner implements ApplicationRunner, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        int code = SpringApplication.exit(applicationContext);
        log.info("Exit with {}.", code);
        System.exit(code);  // 終止應用程式
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
```

**重要功能：**
- 取得 Spring 應用程式上下文
- 調用 `SpringApplication.exit()` 取得退出碼
- 使用 `System.exit()` 終止應用程式

## 進階說明

### 環境變數
```properties
# 應用程式類型設定
spring.main.web-application-type=none

# 日誌等級設定
logging.level.tw.fengqing.spring.hello=INFO
```

### 設定檔說明
```properties
# application.properties 主要設定
# 關閉 Web 應用程式功能，專注於命令行操作
spring.main.web-application-type=none
```

## 參考資源

- [Spring Boot 官方文件](https://spring.io/projects/spring-boot)
- [Spring Boot CommandLineRunner 文件](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.spring-application.command-line-runner)
- [Spring Boot ApplicationRunner 文件](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.spring-application.application-runner)
- [Spring Boot Exit Code 文件](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.spring-application.exit-code)

## 注意事項與最佳實踐

### ⚠️ 重要提醒

| 項目 | 說明 | 建議做法 |
|------|------|----------|
| 退出碼管理 | 非零退出碼表示異常 | 根據業務邏輯設定適當的退出碼 |
| 執行順序 | Runner 組件執行順序 | 使用 `@Order` 註解控制執行順序 |
| 資源清理 | 應用程式結束時的清理工作 | 實作 `DisposableBean` 或使用 `@PreDestroy` |
| 日誌記錄 | 命令行應用程式的日誌輸出 | 使用結構化日誌，便於後續分析 |

### 🔒 最佳實踐指南

- **Runner 組件設計**：將複雜的初始化邏輯分離到不同的 Runner 中
- **退出碼管理**：根據不同的錯誤情況設定不同的退出碼
- **資源管理**：確保應用程式結束時正確釋放資源
- **錯誤處理**：在 Runner 中妥善處理異常情況
- **配置管理**：使用 `application.properties` 或環境變數管理配置

## 常見問題與解決方案

### Q: 為什麼應用程式會以退出碼 1 結束？
A: 這是預期的行為。`MyExitCodeGenerator` 設定了退出碼為 1，`ExitApplicationRunner` 會調用 `System.exit(1)` 來終止應用程式。

### Q: 如何修改退出碼？
A: 修改 `MyExitCodeGenerator` 的 `getExitCode()` 方法返回值，或新增其他 `ExitCodeGenerator` 實作。

### Q: 如何調整 Runner 執行順序？
A: 修改 `@Order` 註解的值，數字越小執行越早。

### Q: 如何移除退出功能？
A: 移除 `ExitApplicationRunner` 和 `MyExitCodeGenerator`，或修改 `ExitApplicationRunner` 不調用 `System.exit()`。

## 授權說明

本專案採用 MIT 授權條款，詳見 LICENSE 檔案。

## 關於我們

我們主要專注在敏捷專案管理、物聯網（IoT）應用開發和領域驅動設計（DDD）。喜歡把先進技術和實務經驗結合，打造好用又靈活的軟體解決方案。

## 聯繫我們

- **FB 粉絲頁**：[風清雲談 | Facebook](https://www.facebook.com/profile.php?id=61576838896062)
- **LinkedIn**：[linkedin.com/in/chu-kuo-lung](https://www.linkedin.com/in/chu-kuo-lung)
- **YouTube 頻道**：[雲談風清 - YouTube](https://www.youtube.com/channel/UCXDqLTdCMiCJ1j8xGRfwEig)
- **風清雲談 部落格**：[風清雲談](https://blog.fengqing.tw/)
- **電子郵件**：[fengqing.tw@gmail.com](mailto:fengqing.tw@gmail.com)

---

**📅 最後更新：2025-08-08**  
**👨‍💻 維護者：風清雲談團隊**
