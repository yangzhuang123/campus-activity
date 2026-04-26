---
name: "task-archive"
description: "Archives deleted tasks by saving task content to docs directory. Invoke when user deletes a task or asks to save/archive a task."
---

# Task Archive

This skill automatically archives deleted tasks by preserving their content to the docs directory.

## When to Invoke

**CRITICAL: Invoke this skill IMMEDIATELY when:**
- User deletes a task from the task list
- User asks to archive or save a task
- User mentions keeping or preserving task content before deletion

## Archive Rules

### Rule Name
删除任务归档规则

### Rule Description
When a task is deleted from the task window, the task content must be preserved by saving it to the `docs` directory before the deletion is completed.

### Implementation Steps

1. **Before deleting a task**, capture all task content including:
   - Task title/name
   - Task description
   - Task status
   - Task priority
   - Any other relevant metadata
   - Timestamp of deletion

2. **Create archive file** in `docs/` directory with naming format:
   - Pattern: `docs/task_archive_YYYY-MM-DD_HH-MM-SS.md`
   - Or use task-specific naming if user provides one

3. **Archive file content** should include:
   ```markdown
   # Archived Task

   ## Original Information
   - **Deleted At**: YYYY-MM-DD HH:MM:SS
   - **Original Status**: [status]
   - **Original Priority**: [priority]

   ## Task Content
   [Full task description and content]

   ## Deletion Reason (if provided)
   [User's reason for deletion, if any]
   ```

4. **Verify** the archive file was created successfully before proceeding with deletion

## Example Usage

When user says "delete task #3" or uses a delete command:
1. First invoke this skill to archive the task
2. Save content to `docs/task_archive_2026-04-23_19-30-00.md`
3. Then proceed with the deletion

## Important Notes

- Always archive BEFORE deletion, never after
- Include timestamp in filename to prevent conflicts
- Preserve as much context as possible for future reference
- The archived content can be used for project documentation or recovery if needed
