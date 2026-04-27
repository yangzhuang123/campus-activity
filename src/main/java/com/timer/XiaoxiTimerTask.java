package com.timer;

import com.entity.XxiaoxiEntity;
import com.entity.ShetuanhuodongEntity;
import com.service.XxiaoxiService;
import com.service.ShetuanhuodongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 消息通知定时任务
 */
@Component
public class XiaoxiTimerTask {

	@Autowired
	private XxiaoxiService xxiaoxiService;

	@Autowired
	private ShetuanhuodongService shetuanhuodongService;

	/**
	 * 每5分钟检查一次活动，发送活动前1小时提醒
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void checkActivityReminder() {
		// 获取当前时间
		Date now = new Date();
		// 计算1小时后的时间
		Date oneHourLater = new Date(now.getTime() + 60 * 60 * 1000);

		// 查询1小时后开始的活动
		List<ShetuanhuodongEntity> activities = shetuanhuodongService.selectList(
				new com.baomidou.mybatisplus.mapper.EntityWrapper<ShetuanhuodongEntity>()
						.eq("huodongzhuangtai", "进行中")
						.between("kaishishijian", now, oneHourLater)
		);

		for (ShetuanhuodongEntity activity : activities) {
			// 给学生发送提醒
			sendStudentReminder(activity);
			// 给社长发送提醒
			sendShezhangReminder(activity);
		}
	}

	/**
	 * 给学生发送活动提醒
	 */
	private void sendStudentReminder(ShetuanhuodongEntity activity) {
		// 这里需要查询报名该活动的学生
		// 简化处理，实际应该查询huodongbaoming表
		XxiaoxiEntity message = new XxiaoxiEntity();
		message.setYonghu("1"); // 假设学生ID
		message.setYonghutable("xuesheng");
		message.setXiaoxileixing("活动提醒");
		message.setXiaoxibiaoti("活动即将开始");
		message.setXiaoxineirong("您报名的活动" + activity.getBiaoti() + "将在1小时后开始，请准时参加！");
		message.setFabushijian(new Date());
		message.setChushishijian(activity.getKaishishijian());
		message.setYuedu(0);
		xxiaoxiService.sendMessage(message);
	}

	/**
	 * 给社长发送活动提醒
	 */
	private void sendShezhangReminder(ShetuanhuodongEntity activity) {
		XxiaoxiEntity message = new XxiaoxiEntity();
		message.setYonghu(activity.getZhanghao());
		message.setYonghutable("shezhang");
		message.setXiaoxileixing("活动提醒");
		message.setXiaoxibiaoti("活动即将开始");
		message.setXiaoxineirong("您发布的活动" + activity.getBiaoti() + "将在1小时后开始，请做好准备！");
		message.setFabushijian(new Date());
		message.setChushishijian(activity.getKaishishijian());
		message.setYuedu(0);
		xxiaoxiService.sendMessage(message);
	}
}
