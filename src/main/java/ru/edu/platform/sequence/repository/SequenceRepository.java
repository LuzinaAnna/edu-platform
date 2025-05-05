package ru.edu.platform.sequence.repository;

import java.util.List;

public interface SequenceRepository {
    List<Long> take(String sequenceName, Long n);
}
