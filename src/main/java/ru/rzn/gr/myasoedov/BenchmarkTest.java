package ru.rzn.gr.myasoedov;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.openjdk.jmh.annotations.*;

@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 5)
@Measurement(iterations = 20)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkTest {
    private static ArrayList<Entity> data;
    private static ObjectMapper mapper;
    private static byte[] serializeJavaData;
    private static String serializeJaksonData;

    @Setup(Level.Trial)
    public static void setup() throws IOException {
        data = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            Entity entity = new Entity();
            entity.setField1(ThreadLocalRandom.current().nextInt());
            entity.setField2(ThreadLocalRandom.current().nextInt());
            entity.setField3(ThreadLocalRandom.current().nextInt());
            entity.setField4(ThreadLocalRandom.current().nextInt());
            entity.setField5(ThreadLocalRandom.current().nextInt());
            entity.setField6(RandomStringUtils.random(10, true, true));
            entity.setField7(RandomStringUtils.random(10, true, true));
            entity.setField8(RandomStringUtils.random(10, true, true));
            entity.setField9(RandomStringUtils.random(10, true, true));
            entity.setField10(RandomStringUtils.random(10, true, true));
            entity.setInterval(new Interval(ThreadLocalRandom.current().nextInt(), ThreadLocalRandom.current().nextInt()));
            data.add(entity);
        }

        mapper = new ObjectMapper();
        serializeJavaData = SerializationUtils.serialize(data);
        serializeJaksonData = mapper.writeValueAsString(data);
    }

    @Benchmark
    public void serializeJackson() throws JsonProcessingException {
        mapper.writeValueAsString(data);
    }

    @Benchmark
    public void serializeJava() {
        SerializationUtils.serialize(data);
    }

    @Benchmark
    public void deserializeJackson() throws IOException {
        mapper.readValue(serializeJaksonData, Object.class);
    }

    @Benchmark
    public void deserializeJava() {
        SerializationUtils.deserialize(serializeJavaData);
    }
}
