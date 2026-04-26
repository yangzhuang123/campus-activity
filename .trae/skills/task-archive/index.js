/**
 * Task Archive Skill Entry Point
 */

const TaskArchiveSkill = require('./skill');

// Create and export the skill instance
const skill = new TaskArchiveSkill();

// Export the skill
module.exports = {
  skill,
  name: skill.name,
  description: skill.description,
  process: skill.process.bind(skill)
};