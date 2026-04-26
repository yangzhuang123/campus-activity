const fs = require('fs');
const path = require('path');

/**
 * Task Archive Skill
 * Archives deleted tasks by saving task content to docs directory
 */

class TaskArchiveSkill {
  constructor() {
    this.name = 'task-archive';
    this.description = 'Archives deleted tasks by saving task content to docs directory';
  }

  /**
   * Process the task deletion and archive it
   * @param {Object} task - The task to be archived
   * @param {string} reason - Optional deletion reason
   * @returns {Object} Result of the archive operation
   */
  async process(task, reason = '') {
    try {
      // Create docs directory if it doesn't exist
      const docsDir = path.join(__dirname, '..', '..', '..', 'docs');
      if (!fs.existsSync(docsDir)) {
        fs.mkdirSync(docsDir, { recursive: true });
      }

      // Generate timestamp for filename
      const now = new Date();
      const timestamp = now.toISOString()
        .replace(/T/g, '_')
        .replace(/:/g, '-')
        .split('.')[0];

      // Create archive filename
      const filename = `task_archive_${timestamp}.md`;
      const archivePath = path.join(docsDir, filename);

      // Create archive content
      const content = this.generateArchiveContent(task, reason, now);

      // Write archive file
      fs.writeFileSync(archivePath, content);

      return {
        success: true,
        message: `Task archived successfully to ${filename}`,
        archivePath
      };
    } catch (error) {
      console.error('Error archiving task:', error);
      return {
        success: false,
        message: `Failed to archive task: ${error.message}`
      };
    }
  }

  /**
   * Generate archive content in markdown format
   * @param {Object} task - The task to archive
   * @param {string} reason - Deletion reason
   * @param {Date} deletedAt - Deletion timestamp
   * @returns {string} Markdown content
   */
  generateArchiveContent(task, reason, deletedAt) {
    return `# Archived Task

## Original Information
- **Deleted At**: ${deletedAt.toLocaleString()}
- **Original Status**: ${task.status || 'Unknown'}
- **Original Priority**: ${task.priority || 'Unknown'}

## Task Content
${task.content || 'No content provided'}

## Deletion Reason (if provided)
${reason || 'No reason provided'}
`;
  }
}

// Export the skill module
module.exports = TaskArchiveSkill;