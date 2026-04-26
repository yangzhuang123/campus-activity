/**
 * Test script for Task Archive Skill
 */

const taskArchive = require('./index');

// Test task data
const testTask = {
  id: 'test-123',
  content: '测试任务内容：这是一个需要归档的测试任务',
  status: 'completed',
  priority: 'high'
};

// Test the archive functionality
async function testArchive() {
  console.log('Testing Task Archive Skill...');
  console.log('Task to archive:', testTask);
  
  try {
    const result = await taskArchive.process(testTask, '测试归档功能');
    console.log('Archive result:', result);
    
    if (result.success) {
      console.log('✓ Test passed: Task archived successfully');
      console.log('Archive file created at:', result.archivePath);
    } else {
      console.log('✗ Test failed:', result.message);
    }
  } catch (error) {
    console.log('✗ Test failed with error:', error);
  }
}

// Run the test
testArchive();