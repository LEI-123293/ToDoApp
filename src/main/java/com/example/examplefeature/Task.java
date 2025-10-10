package com.example.examplefeature;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Represents a task entity in the To-Do application.
 * <p>
 * Each {@code Task} contains a description, a creation date, and an optional due date.
 * The class is annotated as a JPA entity mapped to the {@code task} table in the database.
 * </p>
 */
@Entity
@Table(name = "task")
public class Task {

    /** Maximum allowed length for the task description. */
    public static final int DESCRIPTION_MAX_LENGTH = 300;

    /** Unique identifier for the task (auto-generated). */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "task_id")
    private Long id;

    /**
     * Text description of the task.
     * Must not be {@code null} and cannot exceed {@link #DESCRIPTION_MAX_LENGTH} characters.
     */
    @Column(name = "description", nullable = false, length = DESCRIPTION_MAX_LENGTH)
    private String description = "";

    /** Timestamp when the task was created. Cannot be {@code null}. */
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    /** Optional due date for completing the task. */
    @Column(name = "due_date")
    @Nullable
    private LocalDate dueDate;

    /**
     * Protected no-argument constructor required by JPA/Hibernate.
     * <p>
     * This constructor should not be used directly in application code.
     * </p>
     */
    protected Task() {
        // Default constructor to satisfy Hibernate requirements
    }

    /**
     * Creates a new {@code Task} instance with a given description and creation date.
     *
     * @param description the task description (must not exceed {@link #DESCRIPTION_MAX_LENGTH})
     * @param creationDate the timestamp when the task is created
     * @throws IllegalArgumentException if the description exceeds the maximum allowed length
     */
    public Task(String description, Instant creationDate) {
        setDescription(description);
        this.creationDate = creationDate;
    }

    /**
     * Returns the unique identifier of this task.
     *
     * @return the task ID, or {@code null} if the task has not been persisted yet
     */
    public @Nullable Long getId() {
        return id;
    }

    /**
     * Returns the textual description of this task.
     *
     * @return the description string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the task description.
     *
     * @param description the new description (must not be {@code null})
     * @throws IllegalArgumentException if the description exceeds {@link #DESCRIPTION_MAX_LENGTH}
     */
    public void setDescription(String description) {
        if (description.length() > DESCRIPTION_MAX_LENGTH) {
            throw new IllegalArgumentException("Description length exceeds " + DESCRIPTION_MAX_LENGTH);
        }
        this.description = description;
    }

    /**
     * Returns the creation timestamp of the task.
     *
     * @return the creation date
     */
    public Instant getCreationDate() {
        return creationDate;
    }

    /**
     * Returns the due date for completing this task, if any.
     *
     * @return the due date, or {@code null} if not set
     */
    public @Nullable LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets or updates the due date for this task.
     *
     * @param dueDate the new due date, or {@code null} to remove the due date
     */
    public void setDueDate(@Nullable LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Compares this task to another object for equality.
     * Two tasks are considered equal if they have the same non-null ID.
     *
     * @param obj the object to compare with
     * @return {@code true} if the other object is a {@code Task} with the same ID; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Task other = (Task) obj;
        return getId() != null && getId().equals(other.getId());
    }

    /**
     * Returns the hash code for this task.
     * <p>
     * The hash code is based only on the class type to ensure it remains stable
     * during the lifetime of the entity (before and after persistence).
     * </p>
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

